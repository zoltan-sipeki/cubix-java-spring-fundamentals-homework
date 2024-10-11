package hu.cubix.hr.zoltan_sipeki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.cubix.hr.zoltan_sipeki.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
