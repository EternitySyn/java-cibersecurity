package ar.edu.centro8.ps.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FranchiseCreateDTO {
    private String name;
    private String location;
    private Long ownerId;
}
