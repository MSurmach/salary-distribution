package com.intexsoft.salary_distribution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
@AllArgsConstructor
public class EmployeeSalaryDto {
    @JsonProperty("mitarbeiterId")
    private Long employeeId;
    @Setter(AccessLevel.NONE)
    private Double salary;

    public void setSalary(Double salary) {
        this.salary = BigDecimal.valueOf(salary)
                .setScale(2, RoundingMode.UP).doubleValue();
    }
}
