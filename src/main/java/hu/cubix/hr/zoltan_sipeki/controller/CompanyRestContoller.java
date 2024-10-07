package hu.cubix.hr.zoltan_sipeki.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.cubix.hr.zoltan_sipeki.dto.CompanyDto;
import hu.cubix.hr.zoltan_sipeki.dto.EmployeeDto;
import hu.cubix.hr.zoltan_sipeki.mapper.CompanyMapper;
import hu.cubix.hr.zoltan_sipeki.mapper.EmployeeMapper;
import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyRestContoller {
    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<CompanyDto> getAllCompanies(@RequestParam Optional<Boolean> full) {
        var companies = companyService.getAllCompanies();
        if (full.orElse(false)) {
            return companyMapper.mapCompanyListToDtoList(companies);
        }

        return companyMapper.mapCompanyListToDtoListWithoutEmployees(companies);
    }

    @GetMapping("/{id}")
    public CompanyDto getCompanyById(@PathVariable long id, @RequestParam Optional<Boolean> full) {
        var company = companyService.getCompanyById(id);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (full.orElse(false)) {
            return companyMapper.mapCompanyToDto(company);
        }

        return companyMapper.mapCompanyToDtoWithoutEmployees(company);
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody CompanyDto company) {
        var c = companyService.createCompany(companyMapper.mapDtoToCompany(company));
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Company with id " + company.getId() + " already exists.");
        }

        return ResponseEntity.created(URI.create("/api/companies/" + company.getId())).build();
    }

    @PutMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable long id, @RequestBody CompanyDto company) {
        var c = companyService.updateCompany(companyMapper.mapDtoToCompany(company));
        if (c == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Company with id" + company.getId() + "does not exist.");
        }

        return company;
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companyService.deleteCompany(id);
    }

    @PostMapping("/{companyId}/employees")
    public ResponseEntity<?> addEmployeeToCompany(@PathVariable long companyId, @RequestBody EmployeeDto employee) {
        var company = companyService.getCompanyById(companyId);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Company with id " + companyId + " does not exist.");
        }

        var e = employeeMapper.mapDtoToEmployee(employee);
        var employees = company.getEmployees();
        if (employees.contains(e)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Employee with id " + e.getId() + " already exists in company with id " + companyId + ".");
        }

        employees.add(e);
        return ResponseEntity.created(URI.create("/api/company/" + companyId + "/employees/" + employee.getId()))
                .build();
    }

    @PutMapping("/{companyId}/employees")
    public CompanyDto updateEmployeesInCompany(@PathVariable long companyId,
            @RequestBody List<EmployeeDto> employees) {
        var company = companyService.getCompanyById(companyId);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Company with id " + companyId + " does not exist.");
        }

        company.setEmployees(employeeMapper.mapDtoListToEmployeeList(employees));
        return companyMapper.mapCompanyToDto(company);
    }

    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public CompanyDto deleteEmployeeFromCompany(@PathVariable long companyId, @PathVariable long employeeId) {
        var company = companyService.getCompanyById(companyId);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Company with id " + companyId + " does not exist.");
        }

        var employees = company.getEmployees();
        employees.removeIf((Employee e) -> e.getId() == employeeId);
        return companyMapper.mapCompanyToDto(company);
    }
}
