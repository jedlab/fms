package com.fms.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.app.web.action.HomeActionBean;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.configuration.ObjectMapperConfigured;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
//@EnableSwagger2
public class SwaggerConfig // extends WebMvcConfigurationSupport
{


    @Bean
    public Docket swaggerApi()
    {
        //
        ParameterBuilder authorization = new ParameterBuilder();
        authorization.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        //
        ParameterBuilder contentType = new ParameterBuilder();
        contentType.name("Content-Type").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        //
        ParameterBuilder acceptHdr = new ParameterBuilder();
        acceptHdr.name("Accept").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        //
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(authorization.build());
        aParameters.add(contentType.build());
        aParameters.add(acceptHdr.build());

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(HomeActionBean.class.getPackage().getName()))
                .paths(PathSelectors.any()).build()
                .directModelSubstitute(LocalDate.class, String.class).globalOperationParameters(aParameters)
                .genericModelSubstitutes(new Class[] { ResponseEntity.class });
    }

    
    
    
    private class SwaggerObjMapper implements ApplicationListener<ObjectMapperConfigured>
    {

        @Override
        public void onApplicationEvent(ObjectMapperConfigured event)
        {
           ObjectMapper objectMapper = event.getObjectMapper();
           
        }
        
    }
    

}