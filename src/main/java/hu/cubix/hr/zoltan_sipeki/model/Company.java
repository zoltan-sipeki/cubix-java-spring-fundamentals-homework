package hu.cubix.hr.zoltan_sipeki.model;

import java.util.List;

public class Company {
    private long id;
    String registrationNumber;
    String name;
    String address;
    List<Employee> employees;

    public Company() {

    }

    public Company(Company other) {
        this.id = other.id;
        this.registrationNumber = other.registrationNumber;
        this.name = other.name;
        this.address = other.address;
        this.employees = other.employees;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
