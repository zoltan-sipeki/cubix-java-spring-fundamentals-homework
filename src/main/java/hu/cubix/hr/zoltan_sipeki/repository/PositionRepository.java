package hu.cubix.hr.zoltan_sipeki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.cubix.hr.zoltan_sipeki.model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
}
