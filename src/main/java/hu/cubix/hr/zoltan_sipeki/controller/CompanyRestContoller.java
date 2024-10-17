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

import hu.cubix.hr.zoltan_sipeki.dto.AvgSalaryByPositionDto;
import hu.cubix.hr.zoltan_sipeki.dto.CompanyDto;
import hu.cubix.hr.zoltan_sipeki.dto.EmployeeDto;
import hu.cubix.hr.zoltan_sipeki.exception.CompanyAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.CompanyNotFoundException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeAlreadyExistsException;
import hu.cubix.hr.zoltan_sipeki.exception.EmployeeNotFoundException;
import hu.cubix.hr.zoltan_sipeki.mapper.CompanyMapper;
import hu.cubix.hr.zoltan_sipeki.mapper.EmployeeMapper;
import hu.cubix.hr.zoltan_sipeki.model.Company;
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
    public List<CompanyDto> getCompanies(@RequestParam Optional<Boolean> full, @RequestParam Optional<Integer> minSalary, @RequestParam Optional<Integer> minHeadCount) {
        List<Company> companies = null;

        if (minSalary.isPresent()) {
            companies = companyService.getCompaniesBySalaryGreaterThan(minSalary.get());
        }
        else if (minHeadCount.isPresent()) {
            companies = companyService.getCompaniesByHeadCountGreaterThan(minHeadCount.get());
        }
        else {
            companies = companyService.getAllCompanies();
        }
        
        if (full.orElse(false)) {
            return companyMapper.mapCompanyListToDtoList(companies);
        }

        return companyMapper.mapCompanyListToDtoListWithoutEmployees(companies);
    }

    @GetMapping("/{id}")
    public CompanyDto getCompanyById(@PathVariable long id, @RequestParam Optional<Boolean> full) {
        try {
            var company = companyService.getCompanyById(id);
            if (full.orElse(false)) {
                return companyMapper.mapCompanyToDto(company);
            }

            return companyMapper.mapCompanyToDtoWithoutEmployees(company);
        } catch (CompanyNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/average-salaries")
    public List<AvgSalaryByPositionDto> getAvgSalariesGroupedByJobOrderedBySalaryDesc(@PathVariable long id) {
        var salaries = companyService.getAvgSalariesGroupedByJobOrderedBySalaryDesc(id);
        return salaries;
    }
    
    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody CompanyDto company) {
        try {
            var c = companyService.createCompany(companyMapper.mapDtoToCompany(company));
            return ResponseEntity.created(URI.create("/api/companies/" + company.getId()))
                    .body(companyMapper.mapCompanyToDto(c));
        } catch (CompanyAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable long id, @RequestBody CompanyDto company) {
        try {
            companyService.updateCompany(companyMapper.mapDtoToCompany(company));
            return company;
        } catch (CompanyNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companyService.deleteCompany(id);
    }

    @PostMapping("/{companyId}/employees")
    public ResponseEntity<?> addEmployeeToCompany(@PathVariable long companyId, @RequestBody EmployeeDto employee) {
        try {
            var company = companyService.addEmployeeToCompany(companyId, employeeMapper.mapDtoToEmployee(employee));
            return ResponseEntity.created(URI.create("/api/company/" + companyId + "/employees/" + employee.getId()))
                    .body(companyMapper.mapCompanyToDto(company));
        } catch (CompanyNotFoundException | EmployeeAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{companyId}/employees")
    public CompanyDto updateEmployeesInCompany(@PathVariable long companyId, @RequestBody List<EmployeeDto> employees) {
        try {
            var company = companyService.updateEmployeesInCompany(companyId, employeeMapper.mapDtoListToEmployeeList(employees));
            return companyMapper.mapCompanyToDto(company);
        } catch (CompanyNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public CompanyDto deleteEmployeeFromCompany(@PathVariable long companyId, @PathVariable long employeeId) {
        try {
            var company = companyService.deleteEmployeeFromCompany(companyId, employeeId);
            return companyMapper.mapCompanyToDto(company);
        } catch (CompanyNotFoundException | EmployeeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
