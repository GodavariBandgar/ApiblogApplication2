package com.bikkadit.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.blog.helper.AppConstants;
import com.bikkadit.blog.payloads.ApiResponse;
import com.bikkadit.blog.payloads.CategoryDto;
import com.bikkadit.blog.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	Logger logger = LoggerFactory.getLogger(CategoryController.class);

	/*
	 * @author Godavari
	 * 
	 * @apiNote This api used for save the details for category
	 * 
	 * @param categoryDto
	 * 
	 * @return
	 */
	@PostMapping("/categories")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

		logger.info("Request entering for save category details");
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		logger.info("Completed Request for saving category details");
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

	}

	/*
	 * @apiNote This api used for update the category details
	 * 
	 * @param categoryDto
	 * 
	 * @param categoryId
	 * 
	 * @return
	 */

	@PutMapping("/categories/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory( @Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer categoryId) {
		logger.info("Initiated Request for update the category with categoryId:{}", categoryId);
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		logger.info("Completed Request for update the category with categoryId:{}", categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);

	}
	/*
	 * @apiNote This api used for delete the category details
	 * 
	 * @param categoryId
	 * 
	 */

	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		logger.info("Initiated Request for delete category with categoryId:{}", categoryId);
		this.categoryService.deleteCategory(categoryId);
		logger.info("Completed Request for delete category with categoryId:{}", categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.CATEGORY_DELETE, true), HttpStatus.OK);

	}
	/*
	 * @apiNote This api used for get the category details with categoryId
	 * 
	 * @param categoryId
	 * 
	 * @return
	 */

	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		logger.info("Initiated Request for get the single category details with categoryId:{}", categoryId);
		CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
		logger.info("Completed Request for get the single category details with categoryId:{}", categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}
	/*
	 * @apiNote This api used for get All the category details
	 *
	 * @return
	 */

	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		logger.info("Initiated Request for get the All category details ");
		List<CategoryDto> allCat = this.categoryService.getCategories();
		logger.info("completed Request for get the All category details ");
		return new ResponseEntity<List<CategoryDto>>(allCat, HttpStatus.OK);

	}

}
