package com.intexsoft.salary_distribution.client;

import com.intexsoft.salary_distribution.dto.some_service.EmployeeTimeTrackingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class SomeServiceClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public SomeServiceClient(RestTemplate restTemplate,
                             @Value("${some-service.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public EmployeeTimeTrackingDto[] fetchTimeTrackingData(final String period) {
        log.info("IN: trying to fetch data for the period: {}", period);
        log.debug("IN: trying to request: {}", baseUrl);
        var responseEntity = restTemplate.getForEntity(baseUrl, EmployeeTimeTrackingDto[].class, period);
        var statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful() && responseEntity.hasBody()) {
            var body = responseEntity.getBody();
            log.info("OUT: Time tracking data is fetched successfully");
            log.debug("OUT: response body size: {}, content: {}", body.length, body);
            return body;
        } else {
            var exceptionMessage = String.format("Failed to request: %s with parameter: period = %s. " +
                    "The client service responded with the status: %s", baseUrl, period, responseEntity.getStatusCode());
            throw new RestClientException(exceptionMessage);
        }
    }
}
