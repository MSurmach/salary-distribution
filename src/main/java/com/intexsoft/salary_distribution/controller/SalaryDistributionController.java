package com.intexsoft.salary_distribution.controller;

import com.intexsoft.salary_distribution.dto.EmployeeSalaryDto;
import com.intexsoft.salary_distribution.dto.ExceptionResponseDto;
import com.intexsoft.salary_distribution.service.SalaryDistributionService;
import com.intexsoft.salary_distribution.validation.annotation.ValidInputPeriod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Salary distribution", description = "Represents the salary distribution API")
public class SalaryDistributionController {

    private final SalaryDistributionService salaryDistributionService;

    @Operation(
            summary = "Get employee's salary distribution in proportion to work time for a period",
            parameters = {
                    @Parameter(description = "Period for which salary distribution should be calculated",
                            name = "period", required = true, example = "2023-03", in = QUERY),
                    @Parameter(description = "Total salary for all employees for the period",
                            name = "totalAmount", required = true, example = "7500.50", in = QUERY),
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Salary distribution calculation was processed succesfully",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EmployeeSalaryDto.class)))),
                    @ApiResponse(
                            responseCode = "400", description = "Bad request. Violating the constraints",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = ExceptionResponseDto.class)))),
                    @ApiResponse(responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponseDto.class)))
            })
    @GetMapping("/salaryDistribution")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeSalaryDto> getSalaryDistribution(@RequestParam @ValidInputPeriod String period,
                                                         @RequestParam @Min(value = 1) Double totalAmount) {
        log.info("IN: request params (period = {}, totalAmount: = {})", period, totalAmount);
        var employeeSalaryDtos = salaryDistributionService.calculateSalaryDistributionWithTotalAmountForPeriod(period, totalAmount);
        log.info("OUT: salary distribution calculated successfully");
        return employeeSalaryDtos;
    }
}
