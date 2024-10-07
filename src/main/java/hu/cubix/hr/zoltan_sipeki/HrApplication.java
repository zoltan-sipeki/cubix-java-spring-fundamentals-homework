package hu.cubix.hr.zoltan_sipeki;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	private SalaryService salaryService;

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var employees = new Employee[] { 
				new Employee(1L, "name1","job1", 10000, LocalDateTime.of(2010, 10, 11, 0, 0)),
				new Employee(2L, "name2","job2", 20000, LocalDateTime.of(2020, 5, 3, 0, 0)),
				new Employee(3L, "name3","job3", 30000, LocalDateTime.of(2013, 2, 21, 0, 0)),
				new Employee(4L, "name4","job4", 40000, LocalDateTime.of(2000, 9, 15, 0, 0)),
				new Employee(5L, "name5","job4", 40000, LocalDateTime.of(2017, 6, 7, 0, 0)), 
				new Employee(5L, "name6","job4", 40000, LocalDateTime.of(2024, 6, 7, 0, 0)) 
		};
		
		for (var employee : employees) {
			salaryService.setNewSalary(employee);		
			System.out.println("New salary for Employee " + employee.getId() + " (first day: " + employee.getFirstDay() + ") is " + employee.getSalary());
	
		}
	}

}
