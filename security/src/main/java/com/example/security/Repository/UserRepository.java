package com.example.security.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.UserEntities;

public interface UserRepository extends JpaRepository<UserEntities,Integer> {
		public  Optional<UserEntities> findByEmail(String email);
}
