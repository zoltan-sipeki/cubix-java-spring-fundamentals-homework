package hu.cubix.hr.zoltan_sipeki.service;

import org.springframework.beans.factory.annotation.Autowired;

import hu.cubix.hr.zoltan_sipeki.configuration.SalaryConfiguration;
import hu.cubix.hr.zoltan_sipeki.model.Employee;

public class DefaultEmployeeService implements EmployeeService {

	@Autowired
	private SalaryConfiguration salaryConfiguration;

	@Override
	public int getPayRaisePercent(Employee employee) {
		return salaryConfiguration.getDefaultPercent();
	}
}
