package com.workmotion.employees.dao;

import com.workmotion.employees.entities.EmployeeDetails;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmployeeDao {
    //TODO: Temporary in-memory data structure used instead of database table
    private Map<Integer, EmployeeDetails> empDetailsMap = new ConcurrentHashMap<>();
    private final Integer EMP_ID_SEQ_DEFAULT = 10000;

    public Integer saveEmployee(EmployeeDetails empDetails) {
        if(empDetails.getEmpId() == null) {
            empDetails.setEmpId((empDetailsMap.isEmpty() ? EMP_ID_SEQ_DEFAULT : Collections.max(empDetailsMap.keySet())) + 1);
        }
        empDetailsMap.put(empDetails.getEmpId(), empDetails);
        return empDetails.getEmpId();
    }

    public EmployeeDetails findEmployee(Integer empId) {
        return empDetailsMap.get(empId);
    }
}
