package hu.cubix.hr.zoltan_sipeki.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.zoltan_sipeki.exception.EmployeeAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeNotFoundException;
import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.repository.EmployeeRepository;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {
    
    @Autowired
    private EmployeeRepository repo;

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    public Employee getEmployeeById(long id) throws EmployeeNotFoundException {
        var employee = repo.findById(id);
        if (!employee.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }

        return employee.get();
    }

    public List<Employee> getEmployeesByMinSalary(int minSalary) {
        return repo.findEmployeesByMinSalary(minSalary);
    }

    public List<Employee> getEmployeesByJob(String job) {
        return repo.findEmployeesByJob(job);
    }

    public List<Employee> getEmployeesByNameStartingWithIgnoreCase(String namePrefix) {
        return repo.findEmployeesByNameStartingWithIgnoreCase(namePrefix);
    }

    public List<Employee> getEmployeesByFirstDayBetween(LocalDateTime start, LocalDateTime end) {
        return repo.findEmployeesByFirstDayBetween(start, end);
    }

    public Employee createEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        if (repo.existsById(employee.getId())) {
            throw new EmployeeAlreadyExistsException(employee.getId());
        }
        
        repo.save(employee);
        return employee;
    }

    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
        if (!repo.existsById(employee.getId())) {
            throw new EmployeeNotFoundException(employee.getId());
        }

        repo.save(employee);
        return employee;
    }

    public void deleteEmployeeById(long id) {
        repo.deleteById(id);
    }
}
