package ar.edu.centro8.ps.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import ar.edu.centro8.ps.jwt.model.Employee;
import ar.edu.centro8.ps.jwt.model.Franchise;
import ar.edu.centro8.ps.jwt.repository.EmployeeRepository;
import ar.edu.centro8.ps.jwt.repository.FranchiseRepository;
import ar.edu.centro8.ps.jwt.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final FranchiseRepository franchiseRepository;
    
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    EmployeeController(EmployeeRepository employeeRepository, FranchiseRepository franchiseRepository) {
        this.employeeRepository = employeeRepository;
        this.franchiseRepository = franchiseRepository;
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('Administrador')")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        return employeeService.updateEmployee(employee);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    @Transactional
    public void deleteEmployee(@PathVariable Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    // Remove employee from all franchises
    List<Franchise> franchises = franchiseRepository.findAll();
    for (Franchise f : franchises) {
        f.getEmployeeList().removeIf(e -> e.getId().equals(id));
    }
   franchiseRepository.saveAll(franchises);

    // Now delete the employee
    employeeRepository.deleteById(id);
}
    
}