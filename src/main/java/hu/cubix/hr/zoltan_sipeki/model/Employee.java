package hu.cubix.hr.zoltan_sipeki.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class Employee {
	private long id;
	private String job;
	private int salary;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime firstDay;
	
	public Employee() {
	}

	public Employee(long id, String job, int salary, LocalDateTime firstDay) {
		super();
		this.id = id;
		this.job = job;
		this.salary = salary;
		this.firstDay = firstDay;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDateTime getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(LocalDateTime firstDay) {
		this.firstDay = firstDay;
	}
}
