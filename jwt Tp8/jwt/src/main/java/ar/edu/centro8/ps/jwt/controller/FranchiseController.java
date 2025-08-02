package ar.edu.centro8.ps.jwt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.centro8.ps.jwt.dto.FranchiseCreateDTO;
import ar.edu.centro8.ps.jwt.dto.FranchiseDTO;
import ar.edu.centro8.ps.jwt.model.Franchise;
import ar.edu.centro8.ps.jwt.model.Owner;
import ar.edu.centro8.ps.jwt.service.FranchiseService;
import ar.edu.centro8.ps.jwt.repository.FranchiseRepository;
import ar.edu.centro8.ps.jwt.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/franchises")
public class FranchiseController {
    @Autowired
    private FranchiseRepository franchiseRepository;
    @Autowired
    private FranchiseService franchiseService;
    @Autowired
    private OwnerRepository ownerRepository;
    
    @GetMapping
    public List<FranchiseDTO> getAllFranchises() {
    return franchiseRepository.findAllWithOwner().stream()
        .map(FranchiseDTO::new)
        .collect(Collectors.toList());
    }

    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public Franchise getFranchiseById(@PathVariable Long id) {
        return franchiseService.getFranchiseById(id);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('Administrador')")
    public Franchise createFranchise(@RequestBody FranchiseCreateDTO dto) {
    Franchise franchise = new Franchise();
    franchise.setName(dto.getName());
    franchise.setLocation(dto.getLocation());
    

    if (dto.getOwnerId() != null) {
        System.out.println("Owner ID recibido: " + dto.getOwnerId());
        Owner owner = ownerRepository.findById(dto.getOwnerId())
    
        .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        System.out.println("Owner encontrado: " + owner.getName());
            franchise.setOwner(owner);
    }

    return franchiseRepository.save(franchise);
}
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public Franchise updateFranchise(@PathVariable Long id, @RequestBody Franchise franchise) {
        franchise.setId(id);
        return franchiseService.updateFranchise(franchise);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    @Transactional
    public void deleteFranchise(@PathVariable Long id) {
        Franchise franchise = franchiseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Franchise not found"));

        // Break many-to-many links
        if (franchise.getEmployeeList() != null) {
            franchise.getEmployeeList().forEach(employee -> {
                employee.getFranchises().remove(franchise); // remove this franchise from employee's list
            });
            franchise.getEmployeeList().clear(); // clear employee list on franchise
        }

        franchiseRepository.delete(franchise);
    }
    
    @PutMapping("/{franchiseId}/employees/{employeeId}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> assignEmployee(
            @PathVariable Long franchiseId,
            @PathVariable Long employeeId
    ) {
        System.out.println("=== Controller method called ===");
        try {
            franchiseService.assignEmployee(franchiseId, employeeId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace(); // This will show the full cause in the console
            return ResponseEntity.badRequest().build();
        }
    }
}

