package hu.cubix.hr.zoltan_sipeki.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import hu.cubix.hr.zoltan_sipeki.model.Employee;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {
    private Map<Long, Employee> employees = new HashMap<>();

    @GetMapping("/")
    public String getEmployees(Map<String, Object> model) {
        model.put("employees", new ArrayList<>(employees.values()));
        model.put("employee", new Employee());
        return "index";
    }
    

    @PostMapping("/employees")
    public String saveEmployee(Employee employee) {
        if (employees.containsKey(employee.getId())) {
            return "redirect:/";
        }

        employees.put(employee.getId(), employee);

        return "redirect:/";
    }

}
