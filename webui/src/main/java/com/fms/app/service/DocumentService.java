package com.fms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.dao.DocumentDao;
import com.fms.app.domain.DocumentEntity;
import com.jedlab.framework.spring.dao.AbstractDAO;
import com.jedlab.framework.spring.service.AbstractCrudService;

@Service
public class DocumentService extends AbstractCrudService<DocumentEntity> {

	@Autowired
	DocumentDao documentDao;

	@Override
	public AbstractDAO<DocumentEntity> getDao() {
		return documentDao;
	}

}
