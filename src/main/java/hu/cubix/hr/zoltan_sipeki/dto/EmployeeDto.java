package hu.cubix.hr.zoltan_sipeki.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

public class EmployeeDto {
    private long id;
    
    @NotEmpty
    private String job;
    
    @NotEmpty
    private String name;

    @Positive
    private int salary;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    @Past
    private LocalDateTime firstDay;

    public EmployeeDto() {
	}

    public EmployeeDto(long id, String name, String job, int salary, LocalDateTime firstDay) {
		super();
		this.id = id;
        this.name = name;
		this.job = job;
		this.salary = salary;
		this.firstDay = firstDay;
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
        result = prime * result + ((job == null) ? 0 : job.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + salary;
        result = prime * result + ((firstDay == null) ? 0 : firstDay.hashCode());
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
        EmployeeDto other = (EmployeeDto) obj;
        if (id != other.id)
            return false;
        if (job == null) {
            if (other.job != null)
                return false;
        } else if (!job.equals(other.job))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (salary != other.salary)
            return false;
        if (firstDay == null) {
            if (other.firstDay != null)
                return false;
        } else if (!firstDay.equals(other.firstDay))
            return false;
        return true;
    }
}
