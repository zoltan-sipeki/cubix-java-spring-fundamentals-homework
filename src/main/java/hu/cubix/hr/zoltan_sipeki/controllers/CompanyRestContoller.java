package hu.cubix.hr.zoltan_sipeki.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.hr.zoltan_sipeki.dtos.CompanyDto;
import hu.cubix.hr.zoltan_sipeki.dtos.EmployeeDto;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/companies")
public class CompanyRestContoller {
    private Map<Long, CompanyDto> companies = new HashMap<>();

    @GetMapping
    public List<CompanyDto> getAllCompanies(@RequestParam Optional<Boolean> full) {
        var list = new ArrayList<>(companies.values());
        if (full.isPresent() && full.get()) {
            return list;
        }

        List<CompanyDto> res = new ArrayList<>();
        for (var company : list) {
            var copy = new CompanyDto(company);
            copy.setEmployees(null);
            res.add(copy);
        }

        return res;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable long id, @RequestParam Optional<Boolean> full) {
        var company = companies.get(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }

        if (full.isPresent() && full.get()) {
            return ResponseEntity.ok(company);
        }

        var copy = new CompanyDto(company);
        copy.setEmployees(null);
        return ResponseEntity.ok(copy);
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody CompanyDto company) {
        var company_ = companies.get(company.getId());
        if (company_ != null) {
            return ResponseEntity.badRequest().build();
        }

        companies.put(company.getId(), company);
        return ResponseEntity.created(URI.create("/api/companies/" + company.getId())).build();
    }

    @PutMapping("/{id}")
    public CompanyDto modifyCompany(@PathVariable long id, @RequestBody CompanyDto company) {
        companies.put(id, company);
        return company;
    }
    
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companies.remove(id);
    }

    @PostMapping("/{companyId}/employees")
    public ResponseEntity<?> addEmployeeToCompany(@PathVariable long companyId, @RequestBody EmployeeDto employee) {
        var company = companies.get(companyId);
        if (company == null) {
            return ResponseEntity.badRequest().build();
        }

        var employees = company.getEmployees();
        if (employees.contains(employee)) {
            return ResponseEntity.badRequest().build();
        }

        employees.add(employee);
        
        return ResponseEntity.created(URI.create("/api/company/" + companyId + "/employees/" + employee.getId())).build();
    }

    @PutMapping("/{companyId}/employees/{employeeId}")
    public ResponseEntity<?> replaceEmployeesInCompany(@PathVariable long companyId, @RequestBody List<EmployeeDto> employees) {
        var company = companies.get(companyId);
        if (company == null) {
            return ResponseEntity.badRequest().build();
        }

        company.setEmployees(employees);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public void deleteEmployeeFromCompany(@PathVariable long companyId, @PathVariable long employeeId) {
        var company = companies.get(companyId);
        if (company == null) {
            return;
        }

        var employees = company.getEmployees();
        employees.removeIf((EmployeeDto employee) -> employee.getId() == employeeId);
    }
}
