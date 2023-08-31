package com.oath.service;

import java.util.List;

import com.oath.entity.Role;

public interface RoleService {

	 Role getRoleByName(String name);

	 List<Role> getALlRoles();
	 
	 Role saveRole(String roleName);
}
