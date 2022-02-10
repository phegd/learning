package com.workmotion.employees.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class EmployeeDetails {
    @JsonProperty(value = "empId")
    private Integer empId;
    @JsonProperty(value = "empFullName", required = true)
    @NotNull(message="Please provide Employee Full Name")
    private String empFullName;
    @JsonProperty(value = "empAge", required = true)
    @NotNull(message="Please provide a valid Age")
    private Byte empAge;
    @JsonProperty(value = "empPhone", required = true)
    @NotNull(message="Please provide a valid Phone Number")
    private String empPhone;
    @JsonProperty(value = "empEmailId", required = true)
    @NotNull(message="Please provide a valid Email Id")
    private String empEmailId;
    @JsonProperty(value = "empAddress")
    private String empAddress;
    @JsonProperty(value = "empPostCode")
    private Integer empPostCode;
    @JsonProperty(value = "empStatus")
    private EmployeeStatus empStatus;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpFullName() {
        return empFullName;
    }

    public void setEmpFullName(String empFullName) {
        this.empFullName = empFullName;
    }

    public Byte getEmpAge() {
        return empAge;
    }

    public void setEmpAge(Byte empAge) {
        this.empAge = empAge;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpEmailId() {
        return empEmailId;
    }

    public void setEmpEmailId(String empEmailId) {
        this.empEmailId = empEmailId;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public Integer getEmpPostCode() {
        return empPostCode;
    }

    public void setEmpPostCode(Integer empPostCode) {
        this.empPostCode = empPostCode;
    }

    public EmployeeStatus getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(EmployeeStatus empStatus) {
        this.empStatus = empStatus;
    }
}
