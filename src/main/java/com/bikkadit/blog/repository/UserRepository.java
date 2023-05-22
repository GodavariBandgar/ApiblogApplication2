package com.bikkadit.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadit.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	
	Optional<User> findByEmail(String email);
	
	

}
