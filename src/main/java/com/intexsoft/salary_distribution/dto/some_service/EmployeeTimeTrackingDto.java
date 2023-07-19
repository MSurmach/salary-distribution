package com.intexsoft.salary_distribution.dto.some_service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
public class EmployeeTimeTrackingDto {
    @JsonProperty("mitarbeiterId")
    private Long employeeId;
    @JsonProperty("beginn")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S][.]")
    private LocalDateTime workStart;
    @JsonProperty("ende")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S][.]")
    private LocalDateTime workEnd;
    @JsonProperty("dauer")
    private Integer workDurationInMins;
}
