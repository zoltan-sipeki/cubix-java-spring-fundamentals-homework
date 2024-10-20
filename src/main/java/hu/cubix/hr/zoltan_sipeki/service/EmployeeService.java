package hu.cubix.hr.zoltan_sipeki.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springdoc.core.converters.models.Sort;
import org.springframework.data.mapping.PropertyReferenceException;

import hu.cubix.hr.zoltan_sipeki.exception.EmployeeAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeNotFoundException;
import hu.cubix.hr.zoltan_sipeki.model.Employee;

public interface EmployeeService {
	int getPayRaisePercent(Employee employee);

    public List<Employee> getAllEmployees(Sort sort) throws PropertyReferenceException;

	public List<Employee> getAllEmployees(Pageable page) throws PropertyReferenceException;

    public Employee getEmployeeById(long id) throws EmployeeNotFoundException;

    public List<Employee> getEmployeesByMinSalary(int minSalary);

    public List<Employee> getEmployeesByJob(String job);

    public List<Employee> getEmployeesByNameStartingWithIgnoreCase(String namePrefix);

    public List<Employee> getEmployeesByFirstDayBetween(LocalDateTime start, LocalDateTime end);

    public Employee createEmployee(Employee employee) throws EmployeeAlreadyExistsException;

    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException;

    public void deleteEmployeeById(long id);

	public void updateSalariesOfEmployeesByPositionAndCompanyAndSalaryLessThan(String positionName, long companyId, int salary);
}
