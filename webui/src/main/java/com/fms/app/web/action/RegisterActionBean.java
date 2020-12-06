package com.fms.app.web.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.faces.application.FacesMessage;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.navigate.Navigate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fms.app.FMSConfiguration;
import com.fms.app.service.UserService;
import com.fms.util.AsyncResult;
import com.fms.util.EncryptionDecryptionURLParam;
import com.jedlab.framework.mail.MailClient;
import com.jedlab.framework.spring.web.SpringViewScope;
import com.jedlab.framework.web.AbstractActionBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component("registerAction")
@ELBeanName("registerAction")
@SpringViewScope
@Slf4j
@Join(path = "/register", to = "/register.xhtml")
public class RegisterActionBean extends AbstractActionBean {

	@Autowired
	private transient UserService userService;

	@Autowired
	private transient MailClient mailClient;

	@Autowired
	private FMSConfiguration conf;

	private RegistrationVO instance;
	
	private String message;
	
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RegistrationVO getInstance() {
		if (instance == null)
			instance = new RegistrationVO();
		return instance;
	}

	public Navigate register() {
		if(userService.isUsernameAvailable(getInstance().getUsername()) == false)
		{
			
//			getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Username Already taken"));
//	        getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			this.message = "Username already taken";
			return null;
		}
		Long userId = userService.register(getInstance().getUsername(), getInstance().getPassword(), getInstance().getEmail());
		getCurrentInstance().getExternalContext().getFlash().put("email", getInstance().getEmail());
		Map<String, Object> model = new HashMap<>();
		model.put("url", conf.getUrl() + "/activation/"
				+ EncryptionDecryptionURLParam.encrypt("" + userId, EncryptionDecryptionURLParam.AES_KEY));
		AsyncResult.runAsync(() -> {
			mailClient.send("info@fms.com", getInstance().getEmail(), "Account Activation", "register.ftl", model);
		}, AsyncResult.LogOpHandler());
		return Navigate.to("/thankyou.xhtml");
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RegistrationVO implements Serializable {
		private String username;

		private String password;

		private String email;
	}

}
