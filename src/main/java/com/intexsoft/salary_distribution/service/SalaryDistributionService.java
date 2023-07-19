package com.intexsoft.salary_distribution.service;

import com.intexsoft.salary_distribution.dto.EmployeeSalaryDto;

import java.util.List;

public interface SalaryDistributionService {
    List<EmployeeSalaryDto> calculateSalaryDistributionWithTotalAmountForPeriod(String period, Double totalAmount);
}
