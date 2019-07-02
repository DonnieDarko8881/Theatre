package com.crud.theatre.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {
    @Value("${info.company.email}")
    private String companyEmail;

    @Value("${info.company.phone}")
    private String companyPhone;

    public String getCompanyDetails() {
        return " mail: " + getCompanyEmail() + " tel:" + getCompanyPhone();
    }
}