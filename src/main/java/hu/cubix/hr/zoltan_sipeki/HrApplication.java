package hu.cubix.hr.zoltan_sipeki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.cubix.hr.zoltan_sipeki.model.Company;
import hu.cubix.hr.zoltan_sipeki.model.CompanyForm;
import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.model.MinSalary;
import hu.cubix.hr.zoltan_sipeki.model.Position;
import hu.cubix.hr.zoltan_sipeki.service.InitDbService;
import hu.cubix.hr.zoltan_sipeki.service.SalaryService;
import hu.cubix.hr.zoltan_sipeki.util.TestDataGenerator;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	private SalaryService salaryService;

	@Autowired
	private InitDbService initDbService;

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initDbService.clearDB();

		var gen = new TestDataGenerator();
		
		var positions = gen.generatePositions();
		initDbService.insertTestData(Position.class, positions);
		
		var companyForms = gen.generateCompanyForms();
		initDbService.insertTestData(CompanyForm.class, companyForms);
		
		var companies = gen.generateCompanies(companyForms);
		initDbService.insertTestData(Company.class, companies);
		
		var employees = gen.generateEmployees(positions, companies);
		initDbService.insertTestData(Employee.class, employees);
		
		var minSalaries = gen.generateMinSalaries(positions, companies);
		initDbService.insertTestData(MinSalary.class, minSalaries);
	}

}
