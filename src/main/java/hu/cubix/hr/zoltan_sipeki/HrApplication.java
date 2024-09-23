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
				new Employee(1, "job1", 10000, LocalDateTime.of(2010, 10, 11, 0, 0)),
				new Employee(2, "job2", 20000, LocalDateTime.of(2020, 5, 3, 0, 0)),
				new Employee(3, "job3", 30000, LocalDateTime.of(2013, 2, 21, 0, 0)),
				new Employee(4, "job4", 40000, LocalDateTime.of(2000, 9, 15, 0, 0)),
				new Employee(5, "job4", 40000, LocalDateTime.of(2017, 6, 7, 0, 0)), 
				new Employee(5, "job4", 40000, LocalDateTime.of(2024, 6, 7, 0, 0)) 
		};
		
		for (int i = 0; i < employees.length; ++i) {
			salaryService.setNewSalary(employees[i]);			
		}
	}

}
