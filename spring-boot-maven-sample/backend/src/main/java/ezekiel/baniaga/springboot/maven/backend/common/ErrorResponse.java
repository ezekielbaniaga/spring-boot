package ezekiel.baniaga.springboot.maven.backend.common;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private String code;
    private String[] messages;
    private LocalDateTime timestamp;

    public ErrorResponse(String code, String message) {
        this(code, new String[]{ message });
    }

    public ErrorResponse(String code, String[] messages) {
        this.code = code;
        this.messages = messages;
        this.timestamp = LocalDateTime.now();
    }
}
