package hu.cubix.hr.zoltan_sipeki.service;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.query.sqm.PathElementException;
import org.springframework.data.domain.Sort.Direction;

import hu.cubix.hr.zoltan_sipeki.exception.EmployeeAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeNotFoundException;
import hu.cubix.hr.zoltan_sipeki.model.Employee;

public interface EmployeeService {
	int getPayRaisePercent(Employee employee);

    public List<Employee> getAllEmployees(String sortBy, Direction sortOrder) throws PathElementException;

	public List<Employee> getAllEmployees(int page, int size, String sortBy, Direction sortOrder) throws PathElementException;

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
