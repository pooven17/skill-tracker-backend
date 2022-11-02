package com.cts.skilltrkr;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;

@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityWebFilterChain corsWebfilterSpringSecurityFilterChain(ServerHttpSecurity http) {
		http.csrf().disable();
		return http.build();
	}

	@Bean
	CorsWebFilter corsWebFilter() {
		var corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Collections.singletonList("*"));
		corsConfig.setMaxAge(8000L);
		corsConfig.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "DELETE", "PUT", "PATCH"));
		corsConfig.addAllowedHeader("*");
		
		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return new CorsWebFilter(source);
	}

}
