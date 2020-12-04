package com.fms.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.jedlab.framework.mail.MailClient;

/**
 *
 * @author Omid Pourhadi
 *
 */
@Configuration
public class MailConfig
{

   @Bean
   @Autowired
   public MailClient mailClient(JavaMailSender emailSender, freemarker.template.Configuration conf)
   {
       MailClient mc = new MailClient();
       mc.setMailSender(emailSender);
       mc.setFreemarkerConfiguration(conf);
       return mc;
   }

}
