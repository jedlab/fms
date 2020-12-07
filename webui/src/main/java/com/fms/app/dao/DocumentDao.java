package com.fms.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fms.app.domain.DocumentEntity;
import com.fms.app.domain.UserEntity;
import com.jedlab.framework.spring.dao.AbstractCrudDAO;

public interface DocumentDao extends AbstractCrudDAO<DocumentEntity> {


}
