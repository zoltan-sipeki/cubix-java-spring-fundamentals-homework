package hu.cubix.hr.zoltan_sipeki.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort.Direction;
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
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeNotFoundException;
import hu.cubix.hr.zoltan_sipeki.mapper.EmployeeMapper;
import hu.cubix.hr.zoltan_sipeki.service.EmployeeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    @Autowired
    private EmployeeMapper mapper;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getAllEmployees(@RequestParam Optional<String> sortBy, @RequestParam Optional<Direction> sortOrder) {
        try {
            var employees = employeeService.getAllEmployees(sortBy.orElse(""), sortOrder.orElse(Direction.ASC));
            return mapper.mapEmployeeListToDtoList(employees);
        }
        catch (InvalidDataAccessApiUsageException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sortBy parameter \"" + sortBy + "\".");
        }
    }
    @GetMapping(params = {"page", "size"})
    public List<EmployeeDto> getEmployeesPaginated(@RequestParam int page, @RequestParam int size, @RequestParam Optional<String> sortBy, @RequestParam Optional<Direction> sortOrder) {
        try {
            var employees = employeeService.getAllEmployees(page, size, sortBy.orElse(""), sortOrder.orElse(Direction.ASC));
            return mapper.mapEmployeeListToDtoList(employees);
        }
        catch (InvalidDataAccessApiUsageException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sortBy parameter \"" + sortBy + "\".");
        }
    }

    @GetMapping(params = "minSalary")
    public List<EmployeeDto> getEmployeesByMinSalary(@RequestParam int minSalary) {
        return mapper.mapEmployeeListToDtoList(employeeService.getEmployeesByMinSalary(minSalary));
    }
    
    @GetMapping(params = "job")
    public List<EmployeeDto> getEmployeesByJob(@RequestParam String job) {
        var employees = employeeService.getEmployeesByJob(job);
        return mapper.mapEmployeeListToDtoList(employees);
    }

    @GetMapping(params = "nameStartsWith")
    public List<EmployeeDto> getEmployeesByNameStartingWithIgnoreCase(@RequestParam String nameStartsWith) {
        var employees = employeeService.getEmployeesByNameStartingWithIgnoreCase(nameStartsWith);
        return mapper.mapEmployeeListToDtoList(employees);
    }

    @GetMapping(params = {"startDate", "endDate"})
    public List<EmployeeDto> getEmployeesByFirstDayBetween(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        var employees = employeeService.getEmployeesByFirstDayBetween(startDate, endDate);
        return mapper.mapEmployeeListToDtoList(employees);
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable long id) {
        try {
            var employee = employeeService.getEmployeeById(id);
            return mapper.mapEmployeeToDto(employee);

        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }    

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeDto employee) {
        try {
            var e = employeeService.createEmployee(mapper.mapDtoToEmployee(employee));
            return ResponseEntity.created(URI.create("/api/employees/" + employee.getId())).body(mapper.mapEmployeeToDto(e));
        }
        catch (EmployeeAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employee) {
        try {
            employee.setId(id);
            employeeService.updateEmployee(mapper.mapDtoToEmployee(employee));
            return employee;
        }
        catch(EmployeeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
    }

}
