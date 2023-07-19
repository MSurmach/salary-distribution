package com.intexsoft.salary_distribution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intexsoft.salary_distribution.controller.SalaryDistributionController;
import com.intexsoft.salary_distribution.service.SalaryDistributionService;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.intexsoft.salary_distribution.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SalaryDistributionController.class)
public class SalaryDistributionControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SalaryDistributionService salaryDistributionService;

    @Test
    @SneakyThrows
    void getSalaryDistribution_ifRequestParamsValid_mustReturnCorrectResponse() {
        Mockito.when(salaryDistributionService.calculateSalaryDistributionWithTotalAmountForPeriod(TEST_PERIOD,
                TEST_TOTAL_AMOUNT)).thenReturn(EXPECTED_EMPLOYEE_SALARY_DTOS);
        var actualBodyResponse = mockMvc.perform(get("/salaryDistribution")
                        .param("period", TEST_PERIOD)
                        .param("totalAmount", String.valueOf(TEST_TOTAL_AMOUNT)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(objectMapper.writeValueAsString(EXPECTED_EMPLOYEE_SALARY_DTOS), actualBodyResponse);
    }

    @Test
    @SneakyThrows
    void getSalaryDistribution_IfPeriodRequestNotValid_mustThrowException() {
        mockMvc.perform(get("/salaryDistribution")
                        .param("period", INVALID_TEST_PERIOD)
                        .param("totalAmount", String.valueOf(TEST_TOTAL_AMOUNT)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));
        verify(salaryDistributionService, never())
                .calculateSalaryDistributionWithTotalAmountForPeriod(INVALID_TEST_PERIOD, TEST_TOTAL_AMOUNT);
    }

    @Test
    @SneakyThrows
    void getSalaryDistribution_IfTotalAmountRequestNotGreaterZero_mustThrowException() {
        mockMvc.perform(get("/salaryDistribution")
                        .param("period", TEST_PERIOD)
                        .param("totalAmount", String.valueOf(NEGATIVE_TOTAL_AMOUNT)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));
        verify(salaryDistributionService, never())
                .calculateSalaryDistributionWithTotalAmountForPeriod(TEST_PERIOD, NEGATIVE_TOTAL_AMOUNT);
    }
}
