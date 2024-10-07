package hu.cubix.hr.zoltan_sipeki.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;

import hu.cubix.hr.zoltan_sipeki.model.Employee;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private Map<Long, Employee> employees = new HashMap<>();

    @GetMapping
    public String getEmployees(Map<String, Object> model) {
        model.put("employees", new ArrayList<>(employees.values()));
        model.put("employee", new Employee());
        return "employees";
    }
    

    @PostMapping
    public String createEmployee(Employee employee) {
        if (employees.containsKey(employee.getId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Employee with the given ID already exists.");
        }

        employees.put(employee.getId(), employee);

        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String getEmployee(Map<String, Object> model, @PathVariable long id) {
        var employee = employees.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        model.put("employee", employee);
        return "edit-employee";
    }
    
    @PutMapping("/{id}")
    public String modifyEmployee(@PathVariable long id, Employee employee) {
        employees.put(id, employee);
        return "redirect:/employees";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable long id) {
        employees.remove(id);
        return "redirect:/employees";
    }
}
