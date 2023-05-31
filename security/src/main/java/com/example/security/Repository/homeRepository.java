package com.example.security.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entities.HomeEntities;

public interface homeRepository extends JpaRepository<HomeEntities, Integer> {

}
