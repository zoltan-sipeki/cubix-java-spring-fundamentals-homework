package hu.cubix.hr.zoltan_sipeki.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import hu.cubix.hr.zoltan_sipeki.configuration.SalaryConfiguration;
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
		var firstDay = employee.getFirstDay();
		var now = LocalDateTime.now();
		
		var years = salaryConfig.getYears();
		var raisePercents = salaryConfig.getRaisePercents();
		for (int i = 0; i < years.length; ++i) {
			if (firstDay.plusNanos(yearToNanoSecs(years[i])).compareTo(now) <= 0) {
				return raisePercents[i];
			}
		}

		return 0;
	}

	private static long yearToNanoSecs(double year) {
		final long NANO_SECONDS_A_YEAR = 31536000000000000L;
		return (long) (year * NANO_SECONDS_A_YEAR);
	}

}
