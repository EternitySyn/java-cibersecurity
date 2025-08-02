package ar.edu.centro8.ps.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.centro8.ps.jwt.model.Employee;
import ar.edu.centro8.ps.jwt.model.Franchise;
import ar.edu.centro8.ps.jwt.repository.EmployeeRepository;
import ar.edu.centro8.ps.jwt.repository.FranchiseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public FranchiseService(FranchiseRepository franchiseRepository, EmployeeRepository employeeRepository) {
        this.franchiseRepository = franchiseRepository;
        this.employeeRepository = employeeRepository;
    }
    
    public void assignEmployee(Long franchiseId, Long employeeId) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new EntityNotFoundException("Franchise not found"));
        Employee employee = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        franchise.getEmployeeList().add(employee);
        franchiseRepository.save(franchise);
        System.out.println("=== Controller method Ended? ===");
        
    }

    public List<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }
    
    public Franchise getFranchiseById(Long id) {
        return franchiseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Franchise no encontrado"));
    }
    
    public Franchise createFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }
    
    public Franchise updateFranchise(Franchise franchise) {
        Franchise existingFranchise = getFranchiseById(franchise.getId());
        existingFranchise.setName(franchise.getName());
        existingFranchise.setLocation(franchise.getLocation());
        return franchiseRepository.save(existingFranchise);
    }
    
    public void deleteFranchise(Long id) {
        franchiseRepository.deleteById(id);
    }
    
}

