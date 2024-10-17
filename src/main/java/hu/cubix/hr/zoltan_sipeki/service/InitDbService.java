package hu.cubix.hr.zoltan_sipeki.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.cubix.hr.zoltan_sipeki.model.Company;
import hu.cubix.hr.zoltan_sipeki.model.CompanyForm;
import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.model.MinSalary;
import hu.cubix.hr.zoltan_sipeki.model.Position;
import hu.cubix.hr.zoltan_sipeki.repository.CompanyFormRepository;
import hu.cubix.hr.zoltan_sipeki.repository.CompanyRepository;
import hu.cubix.hr.zoltan_sipeki.repository.EmployeeRepository;
import hu.cubix.hr.zoltan_sipeki.repository.MinSalaryRepository;
import hu.cubix.hr.zoltan_sipeki.repository.PositionRepository;

@SuppressWarnings("unchecked")
@Service
public class InitDbService {

    @Autowired
    private EmployeeRepository employeRepo;

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private PositionRepository positionRepo;

    @Autowired
    private MinSalaryRepository minSalaryRepo;

    @Autowired
    private CompanyFormRepository companyFormRepo;

    Map<Class<?>, Consumer<List<?>>> batchInserts;

    public InitDbService() {
        batchInserts = new HashMap<>();
        batchInserts.put(Employee.class, list -> employeRepo.saveAll((List<Employee>)list));
        batchInserts.put(MinSalary.class, list -> minSalaryRepo.saveAll((List<MinSalary>)list));
        batchInserts.put(Company.class, list -> companyRepo.saveAll((List<Company>)list));
        batchInserts.put(CompanyForm.class, list -> companyFormRepo.saveAll((List<CompanyForm>)list));
        batchInserts.put(Position.class, list -> positionRepo.saveAll((List<Position>)list));
    }

    public void clearDB() {
        minSalaryRepo.deleteAll();
        employeRepo.deleteAll();
        companyRepo.deleteAll();
        companyFormRepo.deleteAll();
        positionRepo.deleteAll();
    }

    public <T> void insertTestData(Class<T> type, List<T> list) {
        var consumer  = batchInserts.get(type);
        assert(consumer != null);
        consumer.accept(list);
    }

    public void insertTestData(Employee employee) {
        employeRepo.save(employee);
    }

    public void insertTestData(Company company) {
        companyRepo.save(company);
    }
    
    public void insertTestData(Position position) {
        positionRepo.save(position);
    }

    public void insertTestData(CompanyForm form) {
        companyFormRepo.save(form);
    }
    
    public void insertTestData(MinSalary minSalary) {
        minSalaryRepo.save(minSalary);
    }

}
