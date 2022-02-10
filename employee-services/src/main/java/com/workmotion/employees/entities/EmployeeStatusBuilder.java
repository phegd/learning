package com.workmotion.employees.entities;

public class EmployeeStatusBuilder {
    private EmployeeStatus employeeStatus;

    public EmployeeStatusBuilder() {
        this.employeeStatus = new EmployeeStatus();
    }

    public EmployeeStatusBuilder(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public EmployeeStatusBuilder withEmpStatus(EmployeeStates empStatus) {
        employeeStatus.setEmpStatus(empStatus);
        return this;
    }

    public EmployeeStatusBuilder withEmpSecurityStatus(EmployeeStates.SubStates empSecurityStatus) {
        employeeStatus.setEmpSecurityStatus(empSecurityStatus);
        return this;
    }

    public EmployeeStatusBuilder withEmpWorkPermitStatus(EmployeeStates.SubStates empWorkPermitStatus) {
        employeeStatus.setEmpWorkPermitStatus(empWorkPermitStatus);
        return this;
    }

    public EmployeeStatus build() {
        return employeeStatus;
    }
}