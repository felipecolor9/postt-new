package com.lipsoft.postt;

import com.lipsoft.postt.dto.request.UserDTO;
import com.lipsoft.postt.model.Role;
import com.lipsoft.postt.service.UserService;
import com.lipsoft.postt.service.security.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PosttApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosttApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Will run after the app has initialized
	@Bean
	CommandLineRunner run(RoleService roleService, UserService userService) {
		return args -> {

			//Default roles and user for tests, delete before deploying
			roleService.saveRole(new Role(null, "ROLE_USER"));
			roleService.saveRole(new Role(null, "ROLE_MANAGER"));
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));
			roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new UserDTO(null, "user", "user", "user.test@email.com", 30));
			userService.saveUser(new UserDTO(null, "manager", "manager", "manager.test@email.com", 30));
			userService.saveUser(new UserDTO(null, "admin", "admin", "admin.test@email.com", 30));
			userService.saveUser(new UserDTO(null, "superadmin", "superadmin", "superadmin.test@email.com", 30));

			roleService.addRoleToUser("user", "ROLE_USER");
			roleService.addRoleToUser("admin", "ROLE_ADMIN");
			roleService.addRoleToUser("superadmin","ROLE_SUPER_ADMIN");
		};
	}


}
