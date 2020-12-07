package com.fms.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.FMSConfiguration;
import com.fms.app.dao.DocumentDao;
import com.fms.app.domain.DocumentEntity;
import com.fms.app.domain.UserEntity;
import com.fms.util.IOUtil;
import com.jedlab.framework.spring.dao.AbstractDAO;
import com.jedlab.framework.spring.security.AuthenticationUtil;
import com.jedlab.framework.spring.service.AbstractCrudService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentService extends AbstractCrudService<DocumentEntity> {

	@Autowired
	DocumentDao documentDao;
	
	@Autowired
	FMSConfiguration conf;

	@Override
	public AbstractDAO<DocumentEntity> getDao() {
		return documentDao;
	}
	
	
	
	@Override
	protected void beforeInsert(DocumentEntity entity) {
		String fileLocation = conf.getFileLocation();
		fileLocation = fileLocation + "/" +  AuthenticationUtil.getUserId();
		File directory = new File(fileLocation);
		if(directory.exists() == false)
			directory.mkdirs();
		File filePath = new File(fileLocation + "/" + entity.getName());
		entity.setFilePath(filePath.getAbsolutePath());
		//
		UserEntity userEntity = new UserEntity();
		userEntity.setId(AuthenticationUtil.getUserId());
		entity.setOwner(userEntity);
		try(InputStream data = entity.getData(); 
				OutputStream fis = new FileOutputStream(filePath))
		{
			IOUtil.fastCopy(data, fis);
		} catch (IOException e) {
			log.info("",e);
		}
	}

}
