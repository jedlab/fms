package com.fms.app;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "fms")
@Data
@NoArgsConstructor
public class FMSConfiguration
{

    private String dlurl;
    
    private String url;



}
