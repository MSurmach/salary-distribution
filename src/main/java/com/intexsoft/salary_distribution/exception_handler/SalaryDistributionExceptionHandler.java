package com.intexsoft.salary_distribution.exception_handler;

import com.intexsoft.salary_distribution.dto.ExceptionResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class SalaryDistributionExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ExceptionResponseDto> handleConstraintViolations(HttpServletRequest request,
                                                                 ConstraintViolationException exception) {
        logExceptionWithStackTrace(request, exception);
        return exception.getConstraintViolations().stream()
                .map(constraintViolation -> new ExceptionResponseDto(HttpStatus.BAD_REQUEST,
                        constraintViolation.getMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponseDto handleRestClientException(HttpServletRequest request, Throwable exception) {
        logExceptionWithStackTrace(request, exception);
        return new ExceptionResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponseDto handleAnyUncaughtException(HttpServletRequest request, Throwable exception) {
        logExceptionWithStackTrace(request, exception);
        return new ExceptionResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private void logExceptionWithStackTrace(HttpServletRequest request, Throwable exception) {
        log.error("Failed to request \"{}\", {} is thrown",
                request.getRequestURL(), exception.getClass().getSimpleName(), exception);
    }
}