package com.oath.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oath.entity.User;
import com.oath.records.UsernameAndPasswordLoginRequest;
import com.oath.security.TokenService;
import com.oath.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	private final TokenService tokenService;

	private final AuthenticationManager authenticationManager;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok().body(userService.getAllUsers());
	}


	@PostMapping("/token")
	public Map<String, Object> getUsers(@RequestBody UsernameAndPasswordLoginRequest loginRequest) {

		// THIS IS JUST A PLAYGROUND
		// we can customize the request body params to include a boolean value of refresh token
		// if it's true we should include the refresh token in the response
		// we can use also granted type (password or refresh token) to generate a new token from refresh token 
		// after verify it with decoder method and check if it s valid or not
		// we can create another endpoint for example /refreshToken to handle it
		
		// we should handle exceptions and check for example if the refresh token is null or not or if it s valid
		// and return HTTP response.unauthorized
		try {
			System.out.println("/token");
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password()));

			System.out.println("/hello");

			authentication.getName();
			Map<String, String> resp = new HashMap<>();
			resp.put("token", tokenService.generateToken(authentication));
			resp.put("auths", authentication.getAuthorities().toString());

			
			return Map.of("token", tokenService.generateToken(authentication),
					"auths", authentication.getAuthorities()
					);
			
			//return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(resp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
