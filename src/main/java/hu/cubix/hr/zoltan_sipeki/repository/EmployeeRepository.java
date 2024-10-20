package hu.cubix.hr.zoltan_sipeki.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.cubix.hr.zoltan_sipeki.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    @Query("select e from Employee e where e.job = :job")
    public List<Employee> findEmployeesByJob(String job);

    @Query("select e from Employee e where lower(e.name) like lower(:namePrefix) || '%'")
    public List<Employee> findEmployeesByNameStartingWithIgnoreCase(String namePrefix);

    @Query("select e from Employee e where e.firstDay between :start and :end")
    public List<Employee> findEmployeesByFirstDayBetween(LocalDateTime start, LocalDateTime end);

    @Query("select e from Employee e where e.salary > :minSalary")
    public List<Employee> findEmployeesByMinSalary(int minSalary);

    @Modifying
    @Query("update Employee e set e.salary = :salary where e.company.id = :companyId and e.job.name = :positionName and e.salary < salary")
    public void updateSalariesOfEmployeesByPositionAndCompanyAndSalaryLessThan(long companyId, String positionName, int salary);

}
