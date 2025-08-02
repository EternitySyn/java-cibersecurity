package ar.edu.centro8.ps.jwt.controller;

import ar.edu.centro8.ps.jwt.model.Role; 
import ar.edu.centro8.ps.jwt.model.UserSec;
import ar.edu.centro8.ps.jwt.service.IRoleService;
import ar.edu.centro8.ps.jwt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IRoleService roleService;
    
    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<UserSec>> getAllUsers() {
        List<UserSec> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        Optional<UserSec> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {
        Set<Role> roleList = new HashSet<Role>();
        Role readRole;
        
        userSec.setPassword(userService.encriptPassword(userSec.getPassword()));
        
        for (Role role : userSec.getRolesList()) {
            readRole = roleService.findById(role.getId()).orElse(null);
            if (readRole != null) {
                roleList.add(readRole);
            }
        }
        
        if (!roleList.isEmpty()) {
            userSec.setRolesList(roleList);
            UserSec newUser = userService.save(userSec);
            return ResponseEntity.ok(newUser);
        }
        return ResponseEntity.badRequest().build();
    }
}