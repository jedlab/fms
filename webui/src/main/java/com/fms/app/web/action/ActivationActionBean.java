package com.fms.app.web.action;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.navigate.Navigate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fms.app.service.UserService;
import com.fms.util.EncryptionDecryptionURLParam;
import com.jedlab.framework.spring.web.SpringViewScope;
import com.jedlab.framework.web.AbstractActionBean;

import lombok.extern.slf4j.Slf4j;

@Component("activationBean")
@ELBeanName("activationBean")
@SpringViewScope
@Slf4j
@Join(path = "/activation/{uid}", to = "/activation.xhtml?uid={uid}")
public class ActivationActionBean extends AbstractActionBean {

	private String message;

	private String uid;

	@Autowired
	private transient UserService userService;

	public void load() {
		String userId = EncryptionDecryptionURLParam.decrypt(uid, EncryptionDecryptionURLParam.AES_KEY);
		userService.activateUserById(Long.parseLong(userId));
		this.message = "User activated successfully . please Login";
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
