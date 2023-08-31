package com.oath;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oath.entity.User;
import com.oath.records.RsaKeys;
import com.oath.service.RoleService;
import com.oath.service.UserService;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeys.class)
public class OathApplication{

	public static void main(String[] args) {
		SpringApplication.run(OathApplication.class, args);
	}



//	@Bean
//	CommandLineRunner start(RoleService roleService, UserService userService) {
//		return args -> {
//			roleService.saveRole("User");			
//			roleService.saveRole("Admin");
//			roleService.saveRole("Writer");
//			roleService.saveRole("Super_Admin");
//
//
//			User userAD = userService.addNewUser(
//					new User(null, "adminfirstname111", "adminlastname","adminemail","admin", "admin", false, null));
//			userService.addRoleToUser(userAD.getId(), "Admin");
//			userService.addRoleToUser(userAD.getId(), "Super_Admin");
//			userService.addRoleToUser(userAD.getId(), "Writer");
//
//			for (int i = 0; i < 5; i++) {
//			User user = userService.addNewUser(
//					new User(null, "firstname"+i, "lastname"+i,"email"+i,"user"+i, "user"+i, false, null));
//			userService.addRoleToUser(user.getId(), "User");
//
//			}
//
//		};
//	}

}
