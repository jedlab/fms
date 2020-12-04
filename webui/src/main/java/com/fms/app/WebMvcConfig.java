package com.fms.app;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.jedlab.framework.mapper.ModelMapper;
import com.jedlab.framework.spring.security.SecurityHandlerInterceptor;

/**
 *
 * @author Omid Pourhadi
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{

   
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new FormHttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new BufferedImageHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        registry.addInterceptor(localeInterceptor);
        registry.addInterceptor(new SecurityHandlerInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver()
    {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en", "US"));
        localeResolver.setCookieHttpOnly(true);
        
        return localeResolver;
    }

    

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    
    @Bean
    ModelMapper modelMapper()
    {
        ModelMapper mm = new ModelMapper();
        
        return mm;
    }

    

    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
    }

//    @Bean
//    public FilterRegistrationBean corsServletFilter()
//    {
//
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new SimpleCORSFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("corsServletFilter");
//        return registration;
//    }

//    
//    @Bean
//    public ServletRegistrationBean downloadServlet()
//    {
//        ServletRegistrationBean bean = new ServletRegistrationBean(new DownloadServlet(), "/dl/*");
//        return bean;
//    }

    
    
}
