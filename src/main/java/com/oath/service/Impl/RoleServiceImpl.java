package com.oath.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oath.entity.Role;
import com.oath.repository.RoleRepository;
import com.oath.service.RoleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

	private final RoleRepository roleRepository;
	
	
	
//	public RoleServiceImpl(RoleRepository roleRepository) {
//		this.roleRepository = roleRepository;
//	}

	@Override
	public List<Role> getALlRoles() {
		return roleRepository.findAll();
	}
	
	@Override
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name);
	}
	
	@Override
	public Role saveRole(String roleName) {
		Role role = new Role(null,roleName,false);
		return roleRepository.save(role);
	}

	
}
