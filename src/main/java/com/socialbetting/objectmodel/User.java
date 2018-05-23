package com.socialbetting.objectmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "_user")
@Table(name = "_user")
public class User implements UserDetails {

	public static final String ROLE_ADMIN = "admin";

	public static final String ROLE_USER = "user";

	static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;

	@JsonIgnore
	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@JsonIgnore
	@Column(name = "role", nullable = false)
	private String role;

	public User() {

	}

	public User(String username, String nickname, String password, boolean enabled, String role) {
		super();
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = this.role;
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return role;
			}
		});
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// we never lock accounts
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// credentials never expire
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
