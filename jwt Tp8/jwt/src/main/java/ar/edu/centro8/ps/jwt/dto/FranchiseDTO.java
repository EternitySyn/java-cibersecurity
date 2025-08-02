package ar.edu.centro8.ps.jwt.dto;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.centro8.ps.jwt.model.Employee;
import ar.edu.centro8.ps.jwt.model.Franchise;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseDTO {
    private Long id;
    private String name;
    private String location;
    private String ownerName;
    private List<String> employeeNames;

    public FranchiseDTO(Franchise franchise) {
        this.id = franchise.getId();
        this.location = franchise.getLocation();
        this.name = franchise.getName();
        this.ownerName = franchise.getOwner() != null ? franchise.getOwner().getName() : null;
        this.employeeNames = franchise.getEmployeeList()
                                      .stream()
                                      .map(Employee::getName)
                                      .collect(Collectors.toList());
    }

    // getters and setters (or use Lombok if preferred)
}
