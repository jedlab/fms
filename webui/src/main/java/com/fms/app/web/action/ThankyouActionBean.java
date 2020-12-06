package com.fms.app.web.action;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.navigate.Navigate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fms.app.service.UserService;
import com.jedlab.framework.spring.web.SpringViewScope;
import com.jedlab.framework.web.AbstractActionBean;

import lombok.extern.slf4j.Slf4j;

@Component("thankYouBean")
@ELBeanName("thankYouBean")
@SpringViewScope
@Slf4j
@Join(path = "/thankyou", to = "/thankyou.xhtml")
public class ThankyouActionBean extends AbstractActionBean {

	private String email;

	public void load() {
		if (getCurrentInstance().getExternalContext().getFlash() != null
				&& getCurrentInstance().getExternalContext().getFlash().containsKey("email"))
			this.email = (String) getCurrentInstance().getExternalContext().getFlash().get("email");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
