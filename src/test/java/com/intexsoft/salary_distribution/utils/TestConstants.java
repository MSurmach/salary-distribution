package com.intexsoft.salary_distribution.utils;

import com.intexsoft.salary_distribution.dto.EmployeeSalaryDto;

import java.util.List;

public class TestConstants {
    public static final String TEST_PERIOD = "2023-03";
    public static final String INVALID_TEST_PERIOD = "2023.03";
    public static final Double TEST_TOTAL_AMOUNT = 7500.50;
    public static final Double NEGATIVE_TOTAL_AMOUNT = -7500.50;
    public static final List<EmployeeSalaryDto> EXPECTED_EMPLOYEE_SALARY_DTOS;

    static {
        EXPECTED_EMPLOYEE_SALARY_DTOS = List.of(
                EmployeeSalaryDto.builder()
                        .employeeId(1L)
                        .salary(1687.62)
                        .build(),
                EmployeeSalaryDto.builder()
                        .employeeId(2L)
                        .salary(2437.67)
                        .build(),
                EmployeeSalaryDto.builder()
                        .employeeId(3L)
                        .salary(3375.23)
                        .build());
    }
}
