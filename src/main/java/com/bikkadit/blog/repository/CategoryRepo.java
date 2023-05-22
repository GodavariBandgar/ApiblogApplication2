package com.bikkadit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadit.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	

}
