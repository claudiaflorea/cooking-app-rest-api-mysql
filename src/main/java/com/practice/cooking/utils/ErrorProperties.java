package com.practice.cooking.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.error.value")
@Data
public class ErrorProperties {

    private String required;

    private String size;

    private String capital;

    private String positive;

    private String notNull;

    private String range;
    
}
