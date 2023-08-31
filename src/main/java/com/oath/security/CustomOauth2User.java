package com.oath.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class CustomOauth2User implements OAuth2User{

	private OAuth2User oath2User;
	
	@Override
	public Map<String, Object> getAttributes() {
		return this.oath2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.oath2User.getAuthorities();
	}

	@Override
	public String getName() {
		return this.oath2User.getAttribute("name");
	}
	

	
}
