package com.jwt.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@CrossOrigin(origins = "http://localhost:3000")
@ComponentScan(basePackages = "com.jwt.security")
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service
//	) {
//		return args -> {
//			var admin = RegisterRequest.builder()
//					.firstName("Admin")
//					.lastName("Admin")
//					.email("admin@mail.com")
//					.password("password").role(ADMIN.name()).confirmPassword("password")
//					.build();
//			System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//			var manager = RegisterRequest.builder()
//					.firstName("Manager")
//					.lastName("Manager")
//					.email("manager@mail.com")
//					.password("password")
//					.role(MANAGER.name())
//					.confirmPassword("password")
//					.build();
//			System.out.println("Manager token: " + service.register(manager).getAccessToken());
//
//		};
//	}
}
