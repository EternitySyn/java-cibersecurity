package ar.edu.centro8.ps.jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ar.edu.centro8.ps.jwt.model.Employee;
import ar.edu.centro8.ps.jwt.model.Franchise;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
    Optional<Employee> findEmployeeById(Long id);

@Query("SELECT f FROM Franchise f LEFT JOIN FETCH f.owner")
List<Franchise> findAllWithOwner();
}