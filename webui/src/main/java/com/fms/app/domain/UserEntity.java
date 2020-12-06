package com.fms.app.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jedlab.framework.spring.dao.PO;
import com.jedlab.framework.spring.security.SecurityUserContext;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sec_user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends PO implements UserDetails, SecurityUserContext {

	@Column(name = "user_name")
	private String username;

	@Column(name = "passwd")
	private String password;

	@Column(name = "is_enabled")
//	@Type(type = "yes_no")
	private boolean enabled;

	@Column(name = "email")
	private String email;
	
	
	

	public UserEntity(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
