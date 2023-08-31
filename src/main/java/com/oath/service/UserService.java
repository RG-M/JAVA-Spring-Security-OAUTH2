package com.oath.service;

import java.util.List;
import java.util.Optional;

import com.oath.entity.User;

public interface UserService {

	 Optional<User> getUserById(Long id);
	
	 User getUserByUsername(String username);
	 
	 List<User> getAllUsers();
	 
	 User addNewUser(User u);
	
	 boolean addRoleToUser(Long idUser,String roleName);	
}
