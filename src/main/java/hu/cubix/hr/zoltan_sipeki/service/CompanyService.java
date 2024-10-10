package hu.cubix.hr.zoltan_sipeki.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hu.cubix.hr.zoltan_sipeki.exception.CompanyAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.CompanyNotFoundException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.model.Company;
import hu.cubix.hr.zoltan_sipeki.model.Employee;

@Service
public class CompanyService {
    private Map<Long, Company> companies = new HashMap<>();

    public List<Company> getAllCompanies() {
        return new ArrayList<>(companies.values());
    }

    public Company getCompanyById(long id) throws CompanyNotFoundException {
        var company = companies.get(id);
        if (company == null) {
            throw new CompanyNotFoundException(id);
        }

        return company;
    }

    public Company createCompany(Company company) throws CompanyAlreadyExistsException {
        if (companies.containsKey(company.getId())) {
            throw new CompanyAlreadyExistsException(company.getId());
        }

        companies.put(company.getId(), company);
        return company;
    }

    public Company updateCompany(Company company) throws CompanyNotFoundException {
        if (!companies.containsKey(company.getId())) {
            throw new CompanyNotFoundException(company.getId());
        }

        companies.put(company.getId(), company);
        return company;
    }

    public Company updateEmployeesInCompany(long companyId, List<Employee> employees) throws CompanyNotFoundException {
        var company = getCompanyById(companyId);
        company.setEmployees(employees);
        return company;
    }

    public Company addEmployeeToCompany(long companyId, Employee employee) throws CompanyNotFoundException, EmployeeAlreadyExistsException {
        var company = getCompanyById(companyId);
        if (company == null) {
            throw new CompanyNotFoundException(companyId);
        }

        var employees = company.getEmployees();
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyExistsException(employee.getId(), companyId);
        }

        employees.add(employee);
        return company;
    }

    public Company deleteEmployeeFromCompany(long companyId, long employeeId) throws CompanyNotFoundException {
        var company = getCompanyById(companyId);
        if (company == null) {
            throw new CompanyNotFoundException(companyId);
        }

        var employees = company.getEmployees();
        employees.removeIf(e -> e.getId() == employeeId);
        return company;
    }

    public void deleteCompany(long id) {
        companies.remove(id);
    }
}
