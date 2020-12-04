package com.fms.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Omid Pourhadi
 *
 */
@Configuration
public class RestConfig
{

    @Bean
    public RestTemplate restTemplate()
    {        
         RestTemplate restTemplate = new RestTemplate();
//         restTemplate.setErrorHandler(new RestErrorHandler());
         return restTemplate;
    }
    
    
//    @Bean("loadBalancedRestTemplate")
//    @LoadBalanced
//    @Qualifier("loadBalancedRestTemplate")
//    public RestTemplate loadBalancedRestTemplate()
//    {        
//         RestTemplate restTemplate = new RestTemplate();
//         restTemplate.setErrorHandler(new RestErrorHandler());
//         return restTemplate;
//    }

}
