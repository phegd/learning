package com.workmotion.employees.services;

import com.workmotion.employees.entities.EmployeeDetails;

public interface EmployeeService {
    Boolean isValidRequest(EmployeeDetails empDetails);
    Boolean isValidRequest(Integer empId, String targetState, String targetSubState);
    Integer createEmployee(EmployeeDetails empDetails);
    Integer updateEmployeeState(Integer empId, String empState, String empSubState);
    EmployeeDetails findEmployee(Integer empId);
}
