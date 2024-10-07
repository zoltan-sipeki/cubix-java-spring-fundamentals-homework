package hu.cubix.hr.zoltan_sipeki.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hu.cubix.hr.zoltan_sipeki.model.Company;

@Service
public class CompanyService {
    private Map<Long, Company> companies = new HashMap<>();

    public List<Company> getAllCompanies() {
        return new ArrayList<>(companies.values());
    }

    public Company getCompanyById(long id) {
        return companies.get(id);
    }

    public Company createCompany(Company company) {
        if (companies.containsKey(company.getId())) {
            return null;
        }

        companies.put(company.getId(), company);
        return company;
    }

    public Company updateCompany(Company company) {
        if (!companies.containsKey(company.getId())) {
            return null;
        }

        companies.put(company.getId(), company);
        return company;
    }

    public void deleteCompany(long id) {
        companies.remove(id);
    }
}
