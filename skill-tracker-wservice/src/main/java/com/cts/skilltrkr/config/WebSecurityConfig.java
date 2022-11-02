package com.cts.skilltrkr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.skilltrkr.authconfig.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**", "/configuration/security",
						"/swagger-ui.html", "/swagger-ui", "/webjars/**", "**/api-docs/**", "/swagger-ui/**")
				.permitAll();
		http.csrf().disable();
		// Add a filter to validate the tokens with every request
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/swagger-ui", "/webjars/**", "**/api-docs/**",
				"/swagger-ui/**");
	}
}