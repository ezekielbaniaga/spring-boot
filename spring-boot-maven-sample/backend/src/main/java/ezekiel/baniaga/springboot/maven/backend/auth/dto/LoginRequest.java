package ezekiel.baniaga.springboot.maven.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public record LoginRequest(

    @NotBlank
    String username,

    @NotBlank
    String password
){}
