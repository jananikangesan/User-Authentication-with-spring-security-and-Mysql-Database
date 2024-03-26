package com.cbc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cbc.service.LoginService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private LoginService loginService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
		http.authorizeHttpRequests(
				(req)->req
						.requestMatchers("/","/registration","/register").permitAll()
						.requestMatchers("/index").authenticated()
						.anyRequest().authenticated()				
				).formLogin(formLogin ->
                formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/index") // Redirect to index page after successful login
                .permitAll()
        )
        .logout(logout ->
            logout
                .logoutSuccessUrl("/")
                .permitAll()
        )
        .csrf().disable();
			
		return http.build();
	}
	
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		return loginService;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(loginService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}