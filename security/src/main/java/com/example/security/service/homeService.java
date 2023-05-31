package com.example.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.Repository.homeRepository;
import com.example.security.entities.HomeEntities;

@Service
public class homeService {

	
	@Autowired
	private homeRepository homeRepository;
	
	public List<HomeEntities> fetchAllProduct() 
	{
		return homeRepository.findAll();
	}
	public boolean postProduct(HomeEntities home) 
	{
		 HomeEntities enties=homeRepository.save(home);
		 if(enties.getId()>0) {
			 return true;
		 }
		 return false;
	}
}
