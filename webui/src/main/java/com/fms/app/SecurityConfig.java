package com.fms.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fms.app.service.UserDetailService;
import com.fms.app.web.AuthLogoutSuccessHandler;
import com.fms.app.web.AuthSuccessHandler;

/**
 *
 * @author Omid Pourhadi
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(udetailService()).passwordEncoder(passwordEncoder()).and().eraseCredentials(true);
    }
	
	@Bean
    public UserDetailService udetailService()
    {
        return new UserDetailService();
    }

	
	@Bean
    public AuthSuccessHandler authSuccessHandler()
    {
        AuthSuccessHandler auth = new AuthSuccessHandler();
        auth.setDefaultTargetUrl("/secure/dashboard");
        auth.setAlwaysUseDefaultTargetUrl(false);
        auth.setTargetUrlParameter("url");
        return auth;
    }
	
	@Bean
    public AuthLogoutSuccessHandler authLogoutSuccessHandler()
    {
        AuthLogoutSuccessHandler auth = new AuthLogoutSuccessHandler();
//        auth.setRedirectStrategy(new JsfRedirectStrategy());
        auth.setDefaultTargetUrl("/login");
        auth.setAlwaysUseDefaultTargetUrl(false);
        auth.setTargetUrlParameter("url");
        return auth;
    }
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception
    {
        
        http.csrf().disable()
//                .addFilterBefore(new CaptchaResponseFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(new SimpleCORSFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginProcessingUrl("/loginProcess").loginPage("/login").successForwardUrl("/home").failureUrl("/login?error=true")
                .defaultSuccessUrl("/secure/dashboard", false).usernameParameter("username").passwordParameter("password")
                .successHandler(authSuccessHandler()).and().logout().logoutSuccessHandler(authLogoutSuccessHandler())
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/home").and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
                .antMatchers("/primepush/*").permitAll()
                .antMatchers("/ws/*").permitAll()
                .antMatchers("/resources/**", "/webjars/**", "/login", "/api/v1/*").permitAll()
                
                
                .antMatchers("/member*/**", "/secure*/**").authenticated()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/swagger-ui.html").authenticated()
                .and().headers().frameOptions().sameOrigin()
                .and().anonymous().key("webportal").principal("webportal").and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .sessionAuthenticationStrategy(new ChangeSessionIdAuthenticationStrategy());
        
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/resources/**");
    }
	
}
