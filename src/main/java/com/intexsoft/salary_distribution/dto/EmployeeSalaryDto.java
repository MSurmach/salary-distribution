package com.intexsoft.salary_distribution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmployeeSalaryDto {
    @JsonProperty("mitarbeiterId")
    private Long employeeId;
    private Double salary;
}
