package ezekiel.baniaga.springboot.maven.backend.auth.dto;

public record LoginResponse (

    String accessToken,

    String refreshToken
){}
