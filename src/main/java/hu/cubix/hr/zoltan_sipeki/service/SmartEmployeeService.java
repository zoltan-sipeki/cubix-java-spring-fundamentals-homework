package hu.cubix.hr.zoltan_sipeki.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import hu.cubix.hr.zoltan_sipeki.configuration.SalaryConfiguration;
import hu.cubix.hr.zoltan_sipeki.configuration.SalaryConfiguration.SalaryLimit;
import hu.cubix.hr.zoltan_sipeki.model.Employee;

public class SmartEmployeeService implements EmployeeService {

	/*
	 * @Value("#{'${salary.years}'.split(',')}") private double[] salaryYears;
	 * 
	 * @Value("#{'${salary.raisePercents}'.split(',')}") private int[]
	 * raisePercents;
	 * 
	 * @Override public int getPayRaisePercent(Employee employee) { var firstDay =
	 * employee.getFirstDay(); var now = LocalDateTime.now(); for (int i = 0; i <
	 * salaryYears.length; ++i) { if
	 * (firstDay.plusNanos(yearToNanoSecs(salaryYears[i])).compareTo(now) <= 0) {
	 * return raisePercents[i]; } }
	 * 
	 * return 0; }
	 */
	
	@Autowired
	private SalaryConfiguration salaryConfig;
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		List<SalaryLimit> limits = salaryConfig.getSmart().getLimits();
		

		for (var limit : limits) {
			double diff = ChronoUnit.DAYS.between(employee.getFirstDay(), LocalDateTime.now()) / 365.0;
			if (diff > limit.getYear()) {
				return limit.getPercent();
			}
		}

		return 0;
	}
}
