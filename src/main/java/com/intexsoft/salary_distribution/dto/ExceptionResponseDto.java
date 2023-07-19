package com.intexsoft.salary_distribution.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ExceptionResponseDto {
    private String message;
    private int code;
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime time;

    private ExceptionResponseDto() {
        time = LocalDateTime.now();
    }

    public ExceptionResponseDto(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
        code = httpStatus.value();
        message = "Unexpected exception";
    }

    public ExceptionResponseDto(HttpStatus httpStatus, String message) {
        this(httpStatus);
        this.message = message;
    }

    public ExceptionResponseDto(HttpStatus httpStatus, Exception exception) {
        this(httpStatus, exception.getMessage());
    }
}
