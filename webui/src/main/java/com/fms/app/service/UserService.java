package com.fms.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.dao.UserDao;
import com.fms.app.domain.UserEntity;
import com.jedlab.framework.spring.dao.AbstractDAO;
import com.jedlab.framework.spring.service.AbstractCrudService;

@Service
public class UserService extends AbstractCrudService<UserEntity> {

	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

//	public UserService(UserDao userDao) {
//		super();
//		this.userDao = userDao;
//	}

	@Override
	public AbstractDAO<UserEntity> getDao() {
		return userDao;
	}

	@Transactional
	public Long register(String username, String password, String email) {
		String encodedPassword = passwordEncoder.encode(password);
		UserEntity userEntity = new UserEntity(username, encodedPassword, email);
		userDao.save(userEntity);
		return userEntity.getId();
	}
	
	
	@Transactional
	public boolean isUsernameAvailable(String username) {
		
		Optional<UserEntity> userEntityOp = userDao.getByUsername(username);
		if(userEntityOp.isPresent())
			return false;
		return true;
	}
	
	@Transactional
	public Optional<UserEntity> getUserByUsername(String username) {
		return userDao.getByUsername(username);
	}

	@Transactional
	public void activateUserById(long userId) {
		userDao.activateUserById(userId);
	}

}
