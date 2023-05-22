package com.bikkadit.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.blog.helper.AppConstants;
import com.bikkadit.blog.payloads.UserDto;
import com.bikkadit.blog.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	/**
	 * @apiNote this method is a Create a User
	 * @author Godavari
	 * @param user
	 * 
	 * 
	 */
	
	Logger logger= LoggerFactory.getLogger(UserController.class);
    
	@PostMapping("/users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		logger.info("Request for save the User Details");
		UserDto savedUser = userService.createUser(userDto);
		logger.info("Request Completed by User");
		return new ResponseEntity<UserDto>(savedUser, HttpStatus.CREATED);

	}
	
	/*@apiNote This api is for update the user details
	 * @param userDto
	 * @param userId
	 */

	@PutMapping("/users/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) {
		logger.info("Initiated Request for update the user details with userId:{}",userId);
		UserDto updatedUser = userService.updateUser(userDto, userId);
		logger.info("completed request for update the user details with userId:{}",userId);
		return new ResponseEntity<UserDto>(updatedUser, HttpStatus.CREATED);

	}
	
	/*
	 * @apiNote this api is get the all users
	 */

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUser() {
		logger.info("Initiated Request for get the user details");
		List<UserDto> allUsers = userService.getAllUsers();
		logger.info("completed Request for get the all details in User");
		return new ResponseEntity<>(allUsers, HttpStatus.OK);

	}
	
	/*
	 * @apiNote This api is used for get single user
	 * @param userId
	 * @return
	 * 
	 */

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		logger.info("Initiated Request for get the single user details with userId:{}",userId);
		UserDto singleUser = userService.getUserById(userId);
		logger.info("Completed Request for get the single user details with userId:{}",userId);
		
		return new ResponseEntity<UserDto>(singleUser, HttpStatus.OK);

	}
	
	/*
	 * apiNote This api is used for delete the user
	 * @param userId
	 * 
	 */
    //ADMIN
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {

		logger.info("Initiated Request for delete the user details with userId:{}",userId);
		userService.deleteUser(userId);

		logger.info("Initiated Request for delete the user  details with userId:{}",userId);
		return new ResponseEntity<>(AppConstants.USER_DELETE, HttpStatus.OK);

	}

}

// post=create user
// put=update user
// delete=delete User
// Get=getting User
