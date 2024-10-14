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
import hu.cubix.hr.zoltan_sipeki.repository.EmployeeRepository;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

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
        employeeRepo.saveAll(employees);
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
        employeeRepo.saveAll(employees);
        return company;
    }

    public Company updateEmployeesInCompany(long companyId, List<Employee> employees) throws CompanyNotFoundException {
        var company = getCompanyById(companyId);
        company.getEmployees().forEach(employee -> employee.setCompany(null));
        employees.forEach(employee -> employee.setCompany(company));
        company.setEmployees(employees);

        employeeRepo.saveAll(employees);
        companyRepo.save(company);
        return company;
    }

    public Company addEmployeeToCompany(long companyId, Employee employee) throws CompanyNotFoundException, EmployeeAlreadyExistsException {
        var company = getCompanyById(companyId);
        var employees = company.getEmployees();
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyExistsException(employee.getId(), companyId);
        }

        employees.add(employee);
        employee.setCompany(company);

        employeeRepo.save(employee);
        companyRepo.save(company);
        return company;
    }

    public Company deleteEmployeeFromCompany(long companyId, long employeeId) throws CompanyNotFoundException, EmployeeNotFoundException {
        var company = getCompanyById(companyId);
        var employee = employeeRepo.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new EmployeeNotFoundException(employeeId);
        }

        var employees = company.getEmployees();
        employees.removeIf(e -> e.getId() == employeeId);
        employee.setCompany(null);
        
        companyRepo.save(company);
        employeeRepo.save(employee);
        return company;
    }

    public void deleteCompany(long id) {
        companyRepo.deleteById(id);
    }
}
