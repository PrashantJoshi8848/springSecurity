package com.example.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entities.HomeEntities;
import com.example.security.service.homeService;

@RestController
public class HomeController {
	
	@Autowired
	private homeService homeService ;
	
	@GetMapping("/home")
	public List<HomeEntities> Main() {
		return homeService.fetchAllProduct();
	}
	@PostMapping("/home")
	public Map<String,String> addProduct(@RequestBody HomeEntities entities){
		Map<String ,String> map=new HashMap<>();
	boolean isSaved=homeService.postProduct(entities);
		if(isSaved) {
			map.put("messgae","successfully added");
			return map;
		}
			map.put("messgae","failed");
		return map;
	}
	
	@GetMapping("/allcategory")
	public String getCAta() {
		return "you are in category login";
	}

}
