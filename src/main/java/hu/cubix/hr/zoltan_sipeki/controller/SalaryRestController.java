package hu.cubix.hr.zoltan_sipeki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.service.EmployeeService;


@RestController
@RequestMapping("/api/pay-raise")
public class SalaryRestController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public int getPayRaisePercenByEmployee(@RequestBody Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }
    
}
