package hu.cubix.hr.zoltan_sipeki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.zoltan_sipeki.exception.CompanyAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.CompanyNotFoundException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeNotFoundException;
import hu.cubix.hr.zoltan_sipeki.model.Company;
import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.repository.CompanyRepository;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepo;

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public Company getCompanyById(long id) throws CompanyNotFoundException {
        var company = companyRepo.findById(id);
        if (!company.isPresent()) {
            throw new CompanyNotFoundException(id);
        }
        
        return company.get();
    }

    public Company createCompany(Company company) throws CompanyAlreadyExistsException {
        if (companyRepo.existsById(company.getId())) {
            throw new CompanyAlreadyExistsException(company.getId());
        }

        var employees = company.getEmployees();
        employees.forEach(employee -> employee.setCompany(company));
        companyRepo.save(company);
        
        return company;
    }

    public Company updateCompany(Company company) throws CompanyNotFoundException {
        var c = companyRepo.findById(company.getId()).orElse(null);
        if (c == null) {
            throw new CompanyNotFoundException(company.getId());
        }

        c.getEmployees().forEach(employee -> employee.setCompany(null));
        var employees = company.getEmployees();
        employees.forEach(employee -> employee.setCompany(company));

        companyRepo.save(company);
        
        return company;
    }

    public Company updateEmployeesInCompany(long companyId, List<Employee> employees) throws CompanyNotFoundException {
        var company = getCompanyById(companyId);
        company.replace(employees);
        companyRepo.save(company);
        return company;
    }

    public Company addEmployeeToCompany(long companyId, Employee employee) throws CompanyNotFoundException, EmployeeAlreadyExistsException {
        var company = getCompanyById(companyId);
        if (company.has(employee)) {
            throw new EmployeeAlreadyExistsException(employee.getId(), companyId);
        }

        company.add(employee);
        companyRepo.save(company);
        return company;
    }

    public Company deleteEmployeeFromCompany(long companyId, long employeeId) throws CompanyNotFoundException, EmployeeNotFoundException {
        var company = getCompanyById(companyId);
        company.remove(employee -> employee.getId() == employeeId);
        companyRepo.save(company);
        
        return company;
    }

    public void deleteCompany(long id) {
        var company = companyRepo.findById(id).orElse(null);
        if (company == null) {
            return;
        }
        company.removeAllEmployess();
        companyRepo.deleteById(id);
    }
}
