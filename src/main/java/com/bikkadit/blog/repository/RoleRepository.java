package com.bikkadit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadit.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	

}
