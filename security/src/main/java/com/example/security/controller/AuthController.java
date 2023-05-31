package com.example.security.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.security.dto.UserDto;
import com.example.security.entities.UserEntities;
import com.example.security.service.JwtService;
import com.example.security.service.UserService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public Map<String,String> Register(@RequestBody UserEntities user){
		return userService.userRegister(user);
	}
	
	@PostMapping("/login") 
	public String AuthandGetToken(@RequestBody UserDto userDto) {	
		
		Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
				if(auth.isAuthenticated()) {					
					return  jwtService.generateToken(userDto.getUsername());
				}else {
					throw new UsernameNotFoundException("user Not Found");
				}
	}
	
}
