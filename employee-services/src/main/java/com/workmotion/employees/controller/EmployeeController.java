package com.workmotion.employees.controller;

import com.workmotion.employees.entities.EmployeeDetails;
import com.workmotion.employees.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "employees")
class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PutMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createEmployee(@RequestBody @Valid EmployeeDetails empRequest) throws HttpStatusCodeException {
        if (employeeService.isValidRequest(empRequest)) {
            return new ResponseEntity<>(employeeService.createEmployee(empRequest), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>((Integer) null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/update/{empId}/{empState}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> updateEmployeeState(
            @PathVariable Integer empId, @PathVariable String empState) throws HttpStatusCodeException {
        if (employeeService.isValidRequest(empId, empState, null)) {
            return new ResponseEntity<>(employeeService.updateEmployeeState(empId, empState, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Integer) null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/update/{empId}/{empState}/{empSubState}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> updateEmployeeState(
            @PathVariable Integer empId, @PathVariable String empState, @PathVariable String empSubState) throws HttpStatusCodeException {
        if (employeeService.isValidRequest(empId, empState, empSubState)) {
            return new ResponseEntity<>(employeeService.updateEmployeeState(empId, empState, empSubState), HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Integer) null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/search/{empId}", produces = "application/json")
    public ResponseEntity<EmployeeDetails> findEmployee(@PathVariable Integer empId) throws HttpStatusCodeException {
        EmployeeDetails empDetails = employeeService.findEmployee(empId);
        if (empDetails != null) {
            return new ResponseEntity<>(empDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((EmployeeDetails) null, HttpStatus.NOT_FOUND);
        }
    }
}