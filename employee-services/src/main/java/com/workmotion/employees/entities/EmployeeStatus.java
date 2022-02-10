package com.workmotion.employees.entities;

public class EmployeeStatus {
    private EmployeeStates empStatus;
    private EmployeeStates.SubStates empSecurityStatus;
    private EmployeeStates.SubStates empWorkPermitStatus;

    public EmployeeStates getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(EmployeeStates empStatus) {
        this.empStatus = empStatus;
    }

    public EmployeeStates.SubStates getEmpSecurityStatus() {
        return empSecurityStatus;
    }

    public void setEmpSecurityStatus(EmployeeStates.SubStates empSecurityStatus) {
        this.empSecurityStatus = empSecurityStatus;
    }

    public EmployeeStates.SubStates getEmpWorkPermitStatus() {
        return empWorkPermitStatus;
    }

    public void setEmpWorkPermitStatus(EmployeeStates.SubStates empWorkPermitStatus) {
        this.empWorkPermitStatus = empWorkPermitStatus;
    }
}
