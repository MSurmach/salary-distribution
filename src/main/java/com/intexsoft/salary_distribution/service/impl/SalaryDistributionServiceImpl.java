package com.intexsoft.salary_distribution.service.impl;

import com.intexsoft.salary_distribution.client.SomeServiceClient;
import com.intexsoft.salary_distribution.dto.EmployeeSalaryDto;
import com.intexsoft.salary_distribution.dto.some_service.EmployeeTimeTrackingDto;
import com.intexsoft.salary_distribution.service.SalaryDistributionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalaryDistributionServiceImpl implements SalaryDistributionService {

    private final SomeServiceClient someServiceClient;

    @Override
    public List<EmployeeSalaryDto> calculateSalaryDistributionWithTotalAmountForPeriod(final String period,
                                                                                       final Double totalAmount) {
        log.debug("IN: trying to fetch time tracking data from \"some-service\" for period: {} and total amount: {}",
                period, totalAmount);
        var employeeTimeTrackingDtos = Arrays.asList(someServiceClient.fetchTimeTrackingData(period));
        var periodTotalWorkDuration = calculateTotalWorkDurationAllEmployees(employeeTimeTrackingDtos);
        var employeeIdTotalWorkDurationMap = groupTimeTrackingDataByEmployeeIdWithTotalWorkDurationSum(employeeTimeTrackingDtos);
        var employeeSalaryDtos = employeeIdTotalWorkDurationMap.entrySet().stream()
                .map(entry -> EmployeeSalaryDto.builder()
                        .employeeId(entry.getKey())
                        .salary(calculateEmployeeSalaryInProportionToWorkTime(entry.getValue(),
                                totalAmount,
                                periodTotalWorkDuration))
                        .build())
                .toList();
        log.debug("OUT: employeeSalaryDtos size = {}, content = {}", employeeSalaryDtos.size(), employeeSalaryDtos);
        return employeeSalaryDtos;
    }

    private Map<Long, Integer> groupTimeTrackingDataByEmployeeIdWithTotalWorkDurationSum(final List<EmployeeTimeTrackingDto> employeeTimeTrackingDtos) {
        log.debug("IN: employeeTimeTrackingDtos size = {}, content = {}", employeeTimeTrackingDtos.size(), employeeTimeTrackingDtos);
        var groupedResultMap = employeeTimeTrackingDtos.stream()
                .collect(groupingBy(EmployeeTimeTrackingDto::getEmployeeId,
                        summingInt(EmployeeTimeTrackingDto::getWorkDurationInMins)));
        log.debug("OUT: groupedResultMap size = {}, content = {}", groupedResultMap.size(), groupedResultMap);
        return groupedResultMap;
    }

    private double calculateEmployeeSalaryInProportionToWorkTime(final int employeeTotalWorkDuration,
                                                                 final double totalAmount,
                                                                 final int periodTotalWorkDuration) {
        log.debug("IN: employeeTotalWorkDuration = {}, totalAmount = {}, periodTotalWorkDuration = {}",
                employeeTotalWorkDuration, totalAmount, periodTotalWorkDuration);
        var employeePerformance = (double) employeeTotalWorkDuration / periodTotalWorkDuration;
        var result = employeePerformance * totalAmount;
        log.debug("OUT: the calculated result = {}", result);
        return BigDecimal.valueOf(result)
                .setScale(2, RoundingMode.UP)
                .doubleValue();
    }

    private int calculateTotalWorkDurationAllEmployees(final List<EmployeeTimeTrackingDto> employeeTimeTrackingDtos) {
        log.debug("IN: employeeTimeTrackingDtos size = {}, content = {}",
                employeeTimeTrackingDtos.size(), employeeTimeTrackingDtos);
        var result = employeeTimeTrackingDtos.stream()
                .mapToInt(EmployeeTimeTrackingDto::getWorkDurationInMins)
                .sum();
        log.debug("OUT: the calculated result = {}", result);
        return result;
    }
}
