package com.ruchij.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiServiceConfiguration {
    @Value("${application.api-service.url}")
    private String serviceUrl;

    @Value("${application.api-service.authentication-token}")
    private String authenticationToken;

    public String getServiceUrl() {
        return serviceUrl;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }
}
