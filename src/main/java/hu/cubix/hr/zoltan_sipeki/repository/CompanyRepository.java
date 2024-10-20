package hu.cubix.hr.zoltan_sipeki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.cubix.hr.zoltan_sipeki.dto.AvgSalaryByPositionDto;
import hu.cubix.hr.zoltan_sipeki.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select c from Company c inner join c.employees e where e.salary > :minSalary")
    List<Company> findCompaniesBySalaryGreaterThan(int minSalary);

    @Query("select c from Company c where size(c.employees) > :headCount")
    List<Company> findCompaniesByHeadcountGreaterThan(int headCount);

    @Query("select new hu.cubix.hr.zoltan_sipeki.dto.AvgSalaryByPositionDto(e.job.name, avg(e.salary)) from Company c inner join c.employees e where c.id = :companyId  group by e.job.name order by avg(e.salary) desc")
    List<AvgSalaryByPositionDto> findAvgSalariesGroupedByJobOrderedBySalaryDesc(long companyId);
}
