package ar.edu.centro8.ps.jwt.dto;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.centro8.ps.jwt.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    private Long id;
    private String name;
    private String email;
    private List<String> franchiseNames;

    public OwnerDTO(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.email = owner.getEmail();
        this.franchiseNames = owner.getFranchises()
                                   .stream()
                                   .map(f -> f.getName())
                                   .collect(Collectors.toList());
    }
}
