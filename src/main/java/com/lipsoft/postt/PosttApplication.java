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
	CommandLineRunner run(RoleService roleService) {
		return args -> {

			//Default roles for tests, delete before deploying
			roleService.saveRole(new Role(null, "ROLE_USER"));
			roleService.saveRole(new Role(null, "ROLE_MANAGER"));
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));
			roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

		};
	}
}
