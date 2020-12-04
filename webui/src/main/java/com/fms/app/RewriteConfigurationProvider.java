package com.fms.app;
import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.springframework.stereotype.Component;

/**
 * @author Omid Pourhadi
 *
 */
@Component
public class RewriteConfigurationProvider extends HttpConfigurationProvider
{

    @Override
    public Configuration getConfiguration(ServletContext context)
    {
        return ConfigurationBuilder.begin()
//                .addRule(Join.path("/login").to("/login.xhtml"))
                .addRule(Join.path("/error").to("/error.xhtml"))
                             
                ;
   
        
    
    
    }

    @Override
    public int priority()
    {
        return 0;
    }
}