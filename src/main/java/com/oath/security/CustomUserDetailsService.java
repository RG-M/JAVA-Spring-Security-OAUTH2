package com.oath.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oath.entity.User;
import com.oath.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = userService.getUserByUsername(username);
		if(u != null) {
			return new CustomUserDetails(u);
		}
		else
			throw new UsernameNotFoundException("User not found");
	}
}
