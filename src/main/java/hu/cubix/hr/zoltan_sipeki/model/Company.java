package hu.cubix.hr.zoltan_sipeki.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Company {
    @Id
    @GeneratedValue
    private long id;

    @NaturalId
    private String registrationNumber;
    
    private String name;
    
    private String address;
    
    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    @ManyToOne
    private CompanyForm form;

    public Company() {

    }

    public Company(Company other) {
        this.id = other.id;
        this.registrationNumber = other.registrationNumber;
        this.name = other.name;
        this.address = other.address;
        this.employees = other.employees;
    }

    public CompanyForm getForm() {
        return form;
    }

    public void setForm(CompanyForm form) {
        this.form = form;
    }

    public void add(Employee employee) {
        employees.add(employee);
        employee.setCompany(this);
    }

    public List<Employee> replace(List<Employee> employees) {
        var oldEmployees = this.employees;
        for (var employee : oldEmployees) {
            employee.setCompany(null);
        }
        for (var employee : employees) {
            employee.setCompany(this);
        }
        return oldEmployees;
    }

    public boolean has(Employee employee) {
        return this.employees.contains(employee);
    }

    public List<Employee> removeAllEmployess() {
        var oldEmployees = this.employees;
        for (var employee : oldEmployees) {
            employee.setCompany(null);
        }

        this.employees = new ArrayList<>();
        return oldEmployees;
    }

    public List<Employee> remove(Predicate<Employee> callback) {
        var res = new ArrayList<Employee>();
        for (int i = employees.size() - 1; i >= 0; --i) {
            var employee = employees.get(i);
            if (callback.test(employee)) {
                employee.setCompany(null);
                employees.remove(i);
                res.add(employee);
            }
        }
        return res;
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
        Company other = (Company) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
