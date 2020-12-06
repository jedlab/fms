package com.fms.app.web.action;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.jedlab.framework.spring.security.AuthenticationUtil;
import com.jedlab.framework.web.AbstractActionBean;

@Component("securityBean")
@Scope(WebApplicationContext.SCOPE_SESSION)
@ELBeanName("securityBean")
public class SecurityBean extends AbstractActionBean {

	public boolean isLoggedIn() {
		return AuthenticationUtil.isLoggedIn();
	}

	public boolean isAnonymous() {
		return isLoggedIn() && (AuthenticationUtil.getAuthentication() instanceof AnonymousAuthenticationToken);
	}

}