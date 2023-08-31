package com.oath.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService{

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oath2User = super.loadUser(userRequest);
		
		CustomOauth2User customUser = new CustomOauth2User(oath2User);
		
		System.out.println("CustomOauth2UserService");

		
		System.out.println(customUser.getName());
		
	
		return customUser;
	}
}
