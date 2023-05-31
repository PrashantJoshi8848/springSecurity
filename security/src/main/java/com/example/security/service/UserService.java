package com.example.security.service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.security.Repository.UserRepository;
import com.example.security.config.UserInfoDetails;
import com.example.security.entities.UserEntities;

@Component
public class UserService implements UserDetailsService  {
@Autowired
private UserRepository userRepository;
		
@Autowired
private PasswordEncoder passwordEncoder;


public Map<String, String> userRegister(UserEntities user){
	Map<String, String> map=new HashMap<>();		
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	UserEntities entiry=userRepository.save(user);
		if(entiry.getId()>0) {
			map.put("message","user created sucessfully");
			return  map;
		}
		map.put("message","faild to create User");
		return map;
} 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	Optional<UserEntities> user=userRepository.findByEmail(username);   
	return user.map(UserInfoDetails::new)
			.orElseThrow(()->new UsernameNotFoundException("user Not found"));
		
	}
}
