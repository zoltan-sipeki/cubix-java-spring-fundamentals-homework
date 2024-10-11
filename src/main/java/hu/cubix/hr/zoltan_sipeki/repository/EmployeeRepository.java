package hu.cubix.hr.zoltan_sipeki.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.cubix.hr.zoltan_sipeki.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where e.job = :job")
    List<Employee> findEmployeesByJob(String job);

    @Query("select e from Employee e where lower(e.name) like lower(:namePrefix) || '%'")
    List<Employee> findEmployeesByNameStartingWithIgnoreCase(String namePrefix);

    @Query("select e from Employee e where e.firstDay between :start and :end") 
    List<Employee> findEmployeesByFirstDayBetween(LocalDateTime start, LocalDateTime end);

    @Query("select e from Employee e where e.salary > :minSalary")
    List<Employee> findEmployeesByMinSalary(int minSalary);
}
