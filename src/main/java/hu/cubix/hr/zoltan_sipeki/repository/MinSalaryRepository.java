package hu.cubix.hr.zoltan_sipeki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.cubix.hr.zoltan_sipeki.model.MinSalary;
import hu.cubix.hr.zoltan_sipeki.model.MinSalary.MinSalaryId;

@Repository
public interface MinSalaryRepository extends JpaRepository<MinSalary, MinSalaryId> {
    
    @Query("update MinSalary s set s.minSalary = :minSalary where s.company.id = :companyId and s.position.name = :positionName")
    public void updateMinSalaryByPositionAndCompany(String positionName, long companyId, int minSalary);
}
