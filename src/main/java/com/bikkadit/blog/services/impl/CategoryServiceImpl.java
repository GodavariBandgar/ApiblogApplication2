package com.bikkadit.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.blog.entities.Category;
import com.bikkadit.blog.exceptions.ResourceNotFoundException;
import com.bikkadit.blog.payloads.CategoryDto;
import com.bikkadit.blog.repository.CategoryRepo;
import com.bikkadit.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	Logger logger =LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		logger.info("Initiating dao call for the category details");
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedcat = this.categoryRepo.save(cat);
		logger.info("Completed dao for the save category details");
		return this.modelMapper.map(addedcat, CategoryDto.class);

	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		logger.info("Initiating dao call for the update the category details with:{}",categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category Updatedcat = this.categoryRepo.save(cat);
		logger.info("Completed dao call for the update the category details with:{}",categoryId);
		return this.modelMapper.map(Updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		logger.info("Initiating dao call for the delete the category details with:{}",categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));
		logger.info("Completed dao call for the delete the category details with:{}",categoryId);
		this.categoryRepo.delete(cat);

	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		logger.info("Initiating dao call for the get the category details with:{}",categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Categoty", "category Id", categoryId));
		logger.info("Completed dao call for the get the category details with:{}",categoryId);

		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		logger.info("Initiating dao call for the get all category details");
		List<Category> allcat = this.categoryRepo.findAll();
		List<CategoryDto> collect = allcat.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				
				.collect(Collectors.toList());
		logger.info("Completed dao call for the get all category details");

		return collect;

	}

}
