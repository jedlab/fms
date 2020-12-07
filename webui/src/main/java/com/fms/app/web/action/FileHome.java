package com.fms.app.web.action;

import java.io.IOException;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.navigate.Navigate;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fms.app.domain.DocumentEntity;
import com.fms.app.service.DocumentService;
import com.jedlab.framework.spring.service.AbstractCrudService;
import com.jedlab.framework.spring.web.SpringViewScope;
import com.jedlab.framework.web.AbstractHomeActionBean;

import lombok.extern.slf4j.Slf4j;

@Component("fileHome")
@ELBeanName("fileHome")
@SpringViewScope
@Slf4j
@Join(path = "/secure/fileEdit", to = "/secure/fileEdit.xhtml")
public class FileHome extends AbstractHomeActionBean<DocumentEntity>
{
	
	
	@Autowired
	DocumentService documentService;
	
	@Override
	public AbstractCrudService<DocumentEntity> getService() {
		return documentService;
	}
	
	private UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	
	public Navigate persist()
	{
		//TODO: save the file in db
		try {
			getInstance().setData(getFile().getInputstream());
			getInstance().setName(getFile().getFileName());
			getInstance().setSize(getFile().getSize());
			documentService.insert(getInstance());
		} catch (IOException e) {
			log.info("",e);
		}
		return null;
	}
	
	

}
