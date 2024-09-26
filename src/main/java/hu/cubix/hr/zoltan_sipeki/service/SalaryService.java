package hu.cubix.hr.zoltan_sipeki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.zoltan_sipeki.model.Employee;

@Service
public class SalaryService {
	private EmployeeService employeeService;
	
	public SalaryService(@Autowired EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public void setNewSalary(Employee employee) {
		int raise = employeeService.getPayRaisePercent(employee);
		int salary = employee.getSalary();
		int newSalary = (int)(salary + (double)(salary * raise / 100));
		employee.setSalary(newSalary);
	}
}
