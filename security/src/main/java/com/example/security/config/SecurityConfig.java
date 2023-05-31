package com.example.security.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.security.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private com.example.security.filter.JwtFilter jwtFilter;
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(req->
		  req
		.requestMatchers(HttpMethod.POST,"api/auth/**").permitAll()
		.requestMatchers("/allcategory","/home").hasAuthority("VENDOR")
		.anyRequest()
		.authenticated()
		);
/**		Form login form haru ko lagi matra use hunxa postman ma Authorization header rakhna mildaina
	http.formLogin(Customizer.withDefaults()); **/
		
//		API banuda Postman ma Authorization banuni bela use hunxa
//		http.httpBasic(Customizer.withDefaults());
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authenticationProvider(authProvider());
//		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}
	
	@Bean
	public PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authprov=new DaoAuthenticationProvider();
		authprov.setUserDetailsService(userDetailsService());
		authprov.setPasswordEncoder(encodePassword());
		return authprov;
	}
	
	@Bean
	public AuthenticationManager authenticateManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();		
	}


}
