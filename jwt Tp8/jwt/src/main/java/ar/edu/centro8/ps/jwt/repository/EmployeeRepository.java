package ar.edu.centro8.ps.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ar.edu.centro8.ps.jwt.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
