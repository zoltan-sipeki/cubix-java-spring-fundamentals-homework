package hu.cubix.hr.zoltan_sipeki.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/pay-raise")
public class SalaryRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public int getPayRaisePercenByEmployee(@RequestBody Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }
    
}
