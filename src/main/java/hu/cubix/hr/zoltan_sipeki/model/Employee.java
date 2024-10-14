package hu.cubix.hr.zoltan_sipeki.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {
	@Id
	@GeneratedValue
	private long id;

	private String job;
	
	private String name;
	
	private int salary;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime firstDay;
	
	@ManyToOne
	private Company company;
	
	public Employee() {
	}

	public Employee(long id, String name, String job, int salary, LocalDateTime firstDay) {
		super();
		this.id = id;
		this.name = name;
		this.job = job;
		this.salary = salary;
		this.firstDay = firstDay;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
