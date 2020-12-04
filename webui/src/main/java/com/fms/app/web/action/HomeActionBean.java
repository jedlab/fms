package com.fms.app.web.action;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.stereotype.Component;

import com.jedlab.framework.spring.web.SpringViewScope;

import lombok.extern.slf4j.Slf4j;

@Component("homeAction")
@ELBeanName("homeAction")
@SpringViewScope
@Slf4j
@Join(path = "/home", to = "/home.xhtml")
public class HomeActionBean
{

}
