package hu.cubix.hr.zoltan_sipeki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.hr.zoltan_sipeki.exception.CompanyAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.CompanyNotFoundException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.model.Company;
import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.repository.CompanyRepository;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repo;

    public List<Company> getAllCompanies() {
        return repo.findAll();
    }

    public Company getCompanyById(long id) throws CompanyNotFoundException {
        var company = repo.findById(id);
        if (!company.isPresent()) {
            throw new CompanyNotFoundException(id);
        }
        
        return company.get();
    }

    @Transactional
    public Company createCompany(Company company) throws CompanyAlreadyExistsException {
        if (repo.existsById(company.getId())) {
            throw new CompanyAlreadyExistsException(company.getId());
        }

        repo.save(company);
        return company;
    }

    @Transactional
    public Company updateCompany(Company company) throws CompanyNotFoundException {
        if (!repo.existsById(company.getId())) {
            throw new CompanyNotFoundException(company.getId());
        }

        repo.save(company);
        return company;
    }

    @Transactional
    public Company updateEmployeesInCompany(long companyId, List<Employee> employees) throws CompanyNotFoundException {
        var company = getCompanyById(companyId);
        company.setEmployees(employees);
        repo.save(company);
        return company;
    }

    @Transactional
    public Company addEmployeeToCompany(long companyId, Employee employee) throws CompanyNotFoundException, EmployeeAlreadyExistsException {
        var company = getCompanyById(companyId);
        var employees = company.getEmployees();
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyExistsException(employee.getId(), companyId);
        }

        employees.add(employee);
        repo.save(company);
        return company;
    }

    @Transactional
    public Company deleteEmployeeFromCompany(long companyId, long employeeId) throws CompanyNotFoundException {
        var company = getCompanyById(companyId);
        var employees = company.getEmployees();
        employees.removeIf(e -> e.getId() == employeeId);
        repo.save(company);
        return company;
    }

    @Transactional
    public void deleteCompany(long id) {
        repo.deleteById(id);
    }
}
