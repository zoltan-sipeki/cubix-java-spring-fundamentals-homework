package hu.cubix.hr.zoltan_sipeki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.repository.MinSalaryRepository;

@Service
public class SalaryService {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private MinSalaryRepository minSalaryRepo;
	
	public void setNewSalary(Employee employee) {
		int raise = employeeService.getPayRaisePercent(employee);
		int salary = employee.getSalary();
		int newSalary = (int)(salary + (double)(salary * raise / 100));
		employee.setSalary(newSalary);
	}

	@Transactional
	public void setMinSalary(String positionName, long companyId, int minSalary) {
		minSalaryRepo.updateMinSalaryByPositionAndCompany(positionName, companyId, minSalary);
		employeeService.updateSalariesOfEmployeesByPositionAndCompanyAndSalaryLessThan(positionName, companyId, minSalary);
	}

}
