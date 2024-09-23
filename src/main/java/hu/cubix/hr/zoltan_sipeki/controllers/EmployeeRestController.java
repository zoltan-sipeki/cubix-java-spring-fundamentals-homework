package hu.cubix.hr.zoltan_sipeki.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.hr.zoltan_sipeki.dtos.EmployeeDto;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {
    private Map<Long, EmployeeDto> employees = new HashMap<>();

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable long id) {
        var employee = employees.get(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();    
        }

        return ResponseEntity.ok(employee);
    }

    @GetMapping(params = "salary-gt")
    public List<EmployeeDto> getEmployeesWithSalariesGreaterThan(@RequestParam("salary-gt") int salary) {
        List<EmployeeDto> list = new ArrayList<>();
        for (var employee : employees.values()) {
            if (employee.getSalary() > salary) {
                list.add(employee);
            }
        }
        return list;
    }
    
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employee) {
        if (employees.containsKey(employee.getId())) {
            return ResponseEntity.badRequest().build();
        }

        employees.put((employee.getId()), employee);
        return ResponseEntity.created(URI.create("/employees/" + employee.getId())).build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> modifyEmployee(@PathVariable long id, @RequestBody EmployeeDto employee) {
        var employee_ = employees.get(id);
        if (employee_ == null) {
            return ResponseEntity.notFound().build();
            
        }

        employees.put(id, employee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employees.remove(id);
    }
    
}
