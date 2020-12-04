package com.fms.app;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;

import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.ocpsoft.rewrite.servlet.impl.RewriteServletContextListener;
import org.ocpsoft.rewrite.servlet.impl.RewriteServletRequestListener;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;

//@SpringBootApplication
/**
 *
 * @author Omid Pourhadi
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties(value = FMSConfiguration.class)
@ImportResource(locations = { "classpath*:framework-spring.xml" })
// @Import(ContainerConfig.class)
//@PropertySource({ "classpath:database.properties" })
//@EnableEurekaClient
//@EnableDiscoveryClient
//@EnableFeignClients(basePackageClasses = {CacheServiceProxy.class})
//@RibbonClient("FMS-WEBUI")
public class FMSApplication extends SpringBootServletInitializer implements WebApplicationInitializer
{
    private static final Logger LOGGER = Logger.getLogger(FMSApplication.class.getName());

    @Autowired
    ConfigurableApplicationContext context;

    public static void main(String[] args)
    {
        SpringApplication sp = new SpringApplication(FMSApplication.class);
        sp.setBanner(new Banner() {

            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out)
            {
                out.println("FMS");
            }
        });
        sp.run(args);

    }
    

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(FMSApplication.class, Initializer.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx)
    {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames)
            {

            }

        };
    }

    @Bean
    public RewriteServletRequestListener rewriteServletRequestListener()
    {
        return new RewriteServletRequestListener();
    }

    @Bean
    public RewriteServletContextListener rewriteServletContextListener()
    {
        return new RewriteServletContextListener();
    }

    @Bean
    public FilterRegistrationBean prettyFilter()
    {
        FilterRegistrationBean prettyFilter = new FilterRegistrationBean(new RewriteFilter());
        prettyFilter.setFilter(new RewriteFilter());
        prettyFilter.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR);
        prettyFilter.addUrlPatterns("/*");
        return prettyFilter;
    }

   

    @Bean
    public FilterRegistrationBean fileUploadFilter()
    {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new FileUploadFilter());
        registration.setServletNames(Arrays.asList("Faces Servlet"));
        registration.setName("fileUploadFilter");
        return registration;
    }

  

   

    @Bean
    public ServletRegistrationBean servletRegistrationBean()
    {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.xhtml");
        servletRegistrationBean.setName("Faces Servlet");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

  



}
