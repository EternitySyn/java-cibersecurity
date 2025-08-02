package ar.edu.centro8.ps.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.centro8.ps.jwt.model.Owner;
import ar.edu.centro8.ps.jwt.repository.OwnerRepository;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }
    
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Owner no encontrado"));
    }
    
    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }
    
    public Owner updateOwner(Owner owner) {
        Owner existingOwner = getOwnerById(owner.getId());
        existingOwner.setName(owner.getName());
        existingOwner.setEmail(owner.getEmail());
        return ownerRepository.save(existingOwner);
    }
    
    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}

