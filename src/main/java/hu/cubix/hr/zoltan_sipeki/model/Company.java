package hu.cubix.hr.zoltan_sipeki.model;

import java.util.List;
import java.util.function.Predicate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Company {
    @Id
    @GeneratedValue
    private long id;

    String registrationNumber;
    
    String name;
    
    String address;
    
    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<Employee> employees;

    public Company() {

    }

    public Company(String registrationNumber, String name, String address) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
    }

    public Company(Company other) {
        this.id = other.id;
        this.registrationNumber = other.registrationNumber;
        this.name = other.name;
        this.address = other.address;
        this.employees = other.employees;
    }

    public void add(Employee employee) {
        employees.add(employee);
        employee.setCompany(this);
    }

    public void replace(List<Employee> employees) {
        for (var employee : this.employees) {
            employee.setCompany(null);
        }
        for (var employee : employees) {
            employee.setCompany(this);
        }
        this.employees = employees;
    }

    public boolean has(Employee employee) {
        return this.employees.contains(employee);
    }

    public void removeAllEmployess() {
        for (var employee : employees) {
            employee.setCompany(null);
        }

        this.employees = null;
    }

    public void remove(Predicate<Employee> callback) {
        for (int i = employees.size() - 1; i >= 0; --i) {
            var employee = employees.get(i);
            if (callback.test(employee)) {
                employee.setCompany(null);
                employees.remove(i);
            }
        }
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
