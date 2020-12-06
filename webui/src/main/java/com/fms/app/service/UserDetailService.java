package com.fms.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fms.app.domain.UserEntity;

public class UserDetailService implements UserDetailsService{

	
	@Autowired
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userByUsername = userService.getUserByUsername(username);
		if(userByUsername.isPresent())
		{
			return userByUsername.get();
		}
		throw new UsernameNotFoundException("Invalid username or password");
	}

}
