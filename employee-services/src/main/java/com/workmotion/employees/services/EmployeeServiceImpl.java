package com.workmotion.employees.services;

import com.workmotion.employees.dao.EmployeeDao;
import com.workmotion.employees.entities.EmployeeDetails;
import com.workmotion.employees.entities.EmployeeStates;
import com.workmotion.employees.entities.EmployeeStatus;
import com.workmotion.employees.entities.EmployeeStatusBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;
    private static final String SECURITY_CHECK_CATEGORY = "SECURITY_CHECK";
    private static final String WORK_PERMIT_CHECK_CATEGORY = "WORK_PERMIT_CHECK";

    @Override
    public Boolean isValidRequest(EmployeeDetails empDetails) {
        try {
            if (empDetails == null) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        } catch (Exception ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Boolean isValidRequest(Integer empId, String targetState, String targetSubState) {
        try {
            if (employeeDao.findEmployee(empId) != null) {
                switch (EmployeeStates.valueOf(targetState)) {
                    case IN_CHECK:
                        if (targetSubState != null && EmployeeStates.SubStates.valueOf(targetSubState).getOrder() == 0) {
                            return Boolean.FALSE;
                        }
                        return Boolean.TRUE;
                    case ACTIVE:
                        return Boolean.TRUE;
                    default:
                        return Boolean.FALSE;
                }
            }
        } catch (Exception ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        return Boolean.FALSE;
    }

    @Override
    public Integer createEmployee(EmployeeDetails empDetails) {
        try {
            empDetails.setEmpStatus(new EmployeeStatusBuilder().withEmpStatus(EmployeeStates.ADDED).build());
            return employeeDao.saveEmployee(empDetails);
        } catch (Exception ex) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Integer updateEmployeeState(Integer empId, String empState, String empSubState) {
        try {
            EmployeeDetails empDetails = employeeDao.findEmployee(empId);
            EmployeeStatus empStatus = empDetails.getEmpStatus();
            EmployeeStates.SubStates targetSubState = (empSubState != null) ? EmployeeStates.SubStates.valueOf(empSubState) : EmployeeStates.SubStates.NOT_APPLICABLE;
            String checkCategory = targetSubState.getCategory();
            if (isStateTransitionPermitted(empStatus, checkCategory, EmployeeStates.valueOf(empState), targetSubState)) {
                this.buildEmployeeStatus(empStatus, checkCategory, empState, targetSubState);
                performAutoTransitionOfStates(empStatus);
                empDetails.setEmpStatus(empStatus);
                employeeDao.saveEmployee(empDetails);
                return empDetails.getEmpId();
            } else {
                return -1;
            }
        } catch (Exception ex) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public EmployeeDetails findEmployee(Integer empId) {
        return employeeDao.findEmployee(empId);
    }

    private Boolean isStateTransitionPermitted(EmployeeStatus empStatus, String checkCategory,
                                               EmployeeStates targetState, EmployeeStates.SubStates targetSubState) {
        EmployeeStates.SubStates sourceSubState = SECURITY_CHECK_CATEGORY.equals(checkCategory) ?
                empStatus.getEmpSecurityStatus() : empStatus.getEmpWorkPermitStatus();
        return targetState.isTransitionPermitted(empStatus.getEmpStatus(), targetState, sourceSubState, targetSubState);
    }

    private void buildEmployeeStatus(EmployeeStatus empStatus, String checkCategory, String empState, EmployeeStates.SubStates targetSubState) {
        switch (checkCategory) {
            case SECURITY_CHECK_CATEGORY:
                EmployeeStates.SubStates workPermitStatus = empStatus.getEmpWorkPermitStatus() != null ?
                        empStatus.getEmpWorkPermitStatus() : EmployeeStates.SubStates.WORK_PERMIT_CHECK_STARTED;

                empStatus = new EmployeeStatusBuilder(empStatus)
                        .withEmpStatus(EmployeeStates.IN_CHECK)
                        .withEmpSecurityStatus(targetSubState)
                        .withEmpWorkPermitStatus(workPermitStatus).build();
                break;
            case WORK_PERMIT_CHECK_CATEGORY:
                EmployeeStates.SubStates securityCheckStatus = empStatus.getEmpSecurityStatus() != null ?
                        empStatus.getEmpSecurityStatus() : EmployeeStates.SubStates.SECURITY_CHECK_STARTED;

                empStatus = new EmployeeStatusBuilder(empStatus)
                        .withEmpStatus(EmployeeStates.IN_CHECK)
                        .withEmpWorkPermitStatus(targetSubState)
                        .withEmpSecurityStatus(securityCheckStatus).build();
                break;
            default:
                empStatus = new EmployeeStatusBuilder(empStatus)
                        .withEmpStatus(EmployeeStates.valueOf(empState)).build();
        }
        if(EmployeeStates.IN_CHECK.equals(empStatus.getEmpStatus()) && EmployeeStates.SubStates.NOT_APPLICABLE.equals(targetSubState)) {
            empStatus.setEmpSecurityStatus(EmployeeStates.SubStates.SECURITY_CHECK_STARTED);
            empStatus.setEmpWorkPermitStatus(EmployeeStates.SubStates.WORK_PERMIT_CHECK_STARTED);
        }
    }

    private void performAutoTransitionOfStates(EmployeeStatus empStatus) {
        if (EmployeeStates.IN_CHECK.equals(empStatus.getEmpStatus())
                && EmployeeStates.SubStates.SECURITY_CHECK_FINISHED.equals(empStatus.getEmpSecurityStatus())
                && EmployeeStates.SubStates.WORK_PERMIT_CHECK_FINISHED.equals(empStatus.getEmpWorkPermitStatus())) {
            empStatus.setEmpStatus(EmployeeStates.APPROVED);
        }
    }
}
