package hu.cubix.hr.zoltan_sipeki.service;

import org.springframework.stereotype.Service;

import hu.cubix.hr.zoltan_sipeki.model.Employee;

public class DefaultEmployeeService implements EmployeeService {
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		return 5;
	}
}
