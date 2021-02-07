package com.saman.OAuth2Server;

import com.saman.OAuth2Server.repository.UserRepositoryCRUD;
import com.saman.OAuth2Server.security.model.UserData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
public class OAuth2ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(UserRepositoryCRUD userRepositoryCRUD) {
		return (args) -> {

			// create default user
			UserData c = userRepositoryCRUD.save(new UserData("saman.firouzi@gmail.com", "$2a$08$fL7u5xcvsZl78su29x1ti.dxI.9rYO8t0q5wk2ROJ.1cdR53bmaVG"));

			System.out.println("default user added by ID : " + c.getId());

		};
	}


}
