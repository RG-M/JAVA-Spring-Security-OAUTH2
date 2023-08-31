package com.oath.service.Impl;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oath.entity.Role;
import com.oath.entity.User;
import com.oath.repository.RoleRepository;
import com.oath.repository.UserRepository;
import com.oath.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private  final UserRepository userRepository;
	private  final RoleRepository roleRepository;
	private  final PasswordEncoder passwordEncoder;

//	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
//		this.userRepository = userRepository;
//		this.roleRepository = roleRepository;
//		this.passwordEncoder = passwordEncoder;
//	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User addNewUser(User u) {
		String pw = u.getPassword();
		u.setPassword(passwordEncoder.encode(pw));
		return userRepository.save(u);
	}


	@Override
	public boolean addRoleToUser(Long idUser, String roleName) {
		User user = userRepository.findById(idUser).get();
		Role role = roleRepository.findByName(roleName);

		if(user !=null && role!=null) {
			user.getRoles().add(role);
			return true;
		}
		else
			return false;
	}

}
