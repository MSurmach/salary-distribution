package com.intexsoft.salary_distribution;

import com.intexsoft.salary_distribution.service.SalaryDistributionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import static com.intexsoft.salary_distribution.utils.TestConstants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SalaryDistributionServiceIT extends WireMockBaseTestIT {
    @Autowired
    private SalaryDistributionService salaryDistributionService;

    @Test
    void calculateSalaryDistributionWithTotalAmountForPeriodTest() {
        var actualEmployeeSalaryDtos = salaryDistributionService
                .calculateSalaryDistributionWithTotalAmountForPeriod(TEST_PERIOD, TEST_TOTAL_AMOUNT);
        Assertions.assertEquals(EXPECTED_EMPLOYEE_SALARY_DTOS, actualEmployeeSalaryDtos);
    }
}
