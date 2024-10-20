package hu.cubix.hr.zoltan_sipeki.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springdoc.core.converters.models.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import hu.cubix.hr.zoltan_sipeki.converter.SortConverter;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeNotFoundException;
import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.repository.EmployeeRepository;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private SortConverter sortConverter;

    @Override
    public List<Employee> getAllEmployees(Sort sort) throws PropertyReferenceException {
        return employeeRepo.findAll(sortConverter.convert(sort.getSort()));
    }

    @Override
    public List<Employee> getAllEmployees(Pageable page) throws PropertyReferenceException {
        var res = employeeRepo.findAll(PageRequest.of(page.getPage(), page.getSize(), sortConverter.convert(page.getSort())));
        return res.getContent();
    }

    @Override
    public Employee getEmployeeById(long id) throws EmployeeNotFoundException {
        var employee = employeeRepo.findById(id);
        if (!employee.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }

        return employee.get();
    }

    @Override
    public List<Employee> getEmployeesByMinSalary(int minSalary) {
        return employeeRepo.findEmployeesByMinSalary(minSalary);
    }

    @Override
    public List<Employee> getEmployeesByJob(String job) {
        return employeeRepo.findEmployeesByJob(job);
    }

    @Override
    public List<Employee> getEmployeesByNameStartingWithIgnoreCase(String namePrefix) {
        return employeeRepo.findEmployeesByNameStartingWithIgnoreCase(namePrefix);
    }

    @Override
    public List<Employee> getEmployeesByFirstDayBetween(LocalDateTime start, LocalDateTime end) {
        return employeeRepo.findEmployeesByFirstDayBetween(start, end);
    }

    @Override
    public Employee createEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        if (employeeRepo.existsById(employee.getId())) {
            throw new EmployeeAlreadyExistsException(employee.getId());
        }
        
        employeeRepo.save(employee);
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
        if (!employeeRepo.existsById(employee.getId())) {
            throw new EmployeeNotFoundException(employee.getId());
        }

        employeeRepo.save(employee);
        return employee;
    }

    @Override
    public void deleteEmployeeById(long id) {
        employeeRepo.deleteById(id);
    }

    @Override
    public void updateSalariesOfEmployeesByPositionAndCompanyAndSalaryLessThan(String positionName, long companyId, int salary) {
       employeeRepo.updateSalariesOfEmployeesByPositionAndCompanyAndSalaryLessThan(companyId, positionName, salary);
    }
}
