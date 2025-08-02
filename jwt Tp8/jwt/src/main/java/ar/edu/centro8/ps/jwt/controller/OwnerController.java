package ar.edu.centro8.ps.jwt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.centro8.ps.jwt.dto.OwnerCreateDTO;
import ar.edu.centro8.ps.jwt.dto.OwnerDTO;
import ar.edu.centro8.ps.jwt.model.Owner;
import ar.edu.centro8.ps.jwt.repository.OwnerRepository;
import ar.edu.centro8.ps.jwt.service.OwnerService;

@RestController
@RequestMapping("/api/v1")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private OwnerRepository ownerRepository;
    
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/owners")
    public List<OwnerDTO> getAllOwners() {
    return ownerRepository.findAll()
           .stream()
           .map(OwnerDTO::new)
           .collect(Collectors.toList());
}

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/owners/{id}")
    public Owner getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }
    
    @PostMapping("/owners")
    @PreAuthorize("hasRole('Administrador')")
    public Owner createOwner(@RequestBody OwnerCreateDTO dto) {
    Owner owner = new Owner();
    owner.setName(dto.getName());
    owner.setEmail(dto.getEmail());
    return ownerRepository.save(owner);
}
    
    @PutMapping("/owners/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public Owner updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        owner.setId(id);
        return ownerService.updateOwner(owner);
    }
    
    @DeleteMapping("/owners/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
    }
}
