package com.ankit.webservice.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// All requests should be authenticated
		http.authorizeHttpRequests(auth->auth.anyRequest().authenticated());
		
		// define type of authentication ( basic auth )
		http.httpBasic(Customizer.withDefaults());
		
		// disable csrf because for post and put request usually we have to give csrf tokens
		http.csrf(csrf->csrf.disable());
		
		return http.build();
	}
}
