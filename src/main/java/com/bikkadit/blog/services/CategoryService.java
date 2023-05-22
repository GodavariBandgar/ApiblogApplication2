package com.bikkadit.blog.services;

import java.util.List;

import com.bikkadit.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//create
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete
	void deleteCategory(Integer categoryId);
	
	//get
	CategoryDto getCategoryById(Integer categoryId);
	
	//get All
	List<CategoryDto> getCategories();
	

}
