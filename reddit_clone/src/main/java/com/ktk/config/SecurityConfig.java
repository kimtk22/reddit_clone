package com.ktk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ktk.filter.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@EnableWebSecurity
public class SecurityConfig{
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//			.httpBasic().disable()
//			.csrf().disable()
//			.authorizeHttpRequests()
//				.antMatchers("/api/auth/**")
//				.permitAll()
//			.anyRequest()
////				.authenticated();
//				.permitAll();
//		
//		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//		
//		return http.build();
//	}
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
	
	@Bean
	public SecurityFilterChain securityFilterChainFormLogin(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeHttpRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/").permitAll()
			.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.defaultSuccessUrl("/", true)
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID");
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}