package com.fms.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fms.app.domain.UserEntity;
import com.jedlab.framework.spring.dao.AbstractCrudDAO;

public interface UserDao extends AbstractCrudDAO<UserEntity> {

	@Query("select u from UserEntity u where u.username = :un")
	Optional<UserEntity> getByUsername(@Param("un")String username);

	@Modifying
	@Query("update UserEntity u set u.enabled = true where u.id = :uid")
	void activateUserById(@Param("uid") long userId);

}
