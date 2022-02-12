package com.workmotion.employees.dao;

import com.workmotion.employees.entities.EmployeeDetails;

public interface EmployeeDao {
    Integer saveEmployee(EmployeeDetails empDetails);
    EmployeeDetails findEmployee(Integer empId);
}
