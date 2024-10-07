package hu.cubix.hr.zoltan_sipeki.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.cubix.hr.zoltan_sipeki.dto.EmployeeDto;
import hu.cubix.hr.zoltan_sipeki.mapper.EmployeeMapper;
import hu.cubix.hr.zoltan_sipeki.service.AbstractEmployeeService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    @Autowired
    private EmployeeMapper mapper;

    @Autowired
    private AbstractEmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return mapper.mapEmployeeListToDtoList(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable long id) {
        var employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
        }
        
        return mapper.mapEmployeeToDto(employee);
    }

    @GetMapping(params = "min-salary")
    public List<EmployeeDto> getEmployeesByMinSalary(@RequestParam("min-salary") int salary) {
        return mapper.mapEmployeeListToDtoList(employeeService.getEmployeesByMinSalary(salary));
    }
    
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeDto employee) {
        var e = employeeService.createEmployee(mapper.mapDtoToEmployee(employee));
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with id " + employee.getId() + " already exists.");
        }
        
        return ResponseEntity.created(URI.create("/api/employees/" + employee.getId())).build();
    }
    
    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employee) {
        var e = employeeService.updateEmployee(mapper.mapDtoToEmployee(employee));
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with id " + employee.getId() + " already exists.");
        }        

        return employee;            
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
    }
    
}
