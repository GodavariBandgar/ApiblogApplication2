package com.bikkadit.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bikkadit.blog.entities.Role;
import com.bikkadit.blog.entities.User;
import com.bikkadit.blog.helper.AppConstants;
import com.bikkadit.blog.payloads.UserDto;
import com.bikkadit.blog.repository.RoleRepository;
import com.bikkadit.blog.repository.UserRepository;
import com.bikkadit.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;

	
	Logger logger =LoggerFactory.getLogger(UserServiceImpl.class);
	@Override
	public UserDto createUser(UserDto userDto) {
		logger.info("Initiating dao call for the user details");

		User user = modelMapper.map(userDto, User.class);
		User savedUser = userRepo.save(user);
		UserDto userdto = modelMapper.map(savedUser, UserDto.class);
		
		logger.info("Completed dao for the save user details");
		return userdto;

//		User user = this.dtoToUser(userDto);
//		User savedUser = this.userRepo.save(user);
//		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		logger.info("Initiating dao call for the update the user details with:{}",userId);
		User user2 = userRepo.findById(userId).orElseThrow(() -> new RuntimeException(AppConstants.NOT_FOUND));
		user2.setId(userDto.getId());
		user2.setName(userDto.getName());
		user2.setEmail(userDto.getEmail());
		user2.setPassword(userDto.getPassword());
		user2.setAbout(userDto.getAbout());
		User updatedUser = userRepo.save(user2);
		logger.info("Completed dao call for the update the user details with:{}",userId);
		return modelMapper.map(updatedUser, UserDto.class);

//		User user = this.userRepo.findById(userId).
//				        orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
//		
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		
//		User updatedUser = this.userRepo.save(user);
//		UserDto userDto1 = this.userToDto(updatedUser);
//		
//		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		logger.info("Initiating dao call for get the user details");
		
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException(AppConstants.NOT_FOUND));
		

		logger.info("Completing dao call for get the user details");
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {

		logger.info("Initiating dao call for the getAll user details");
		List<User> allUsers = userRepo.findAll();
		List<UserDto> list = allUsers.stream().map((users) -> modelMapper.map(users, UserDto.class))
				.collect(Collectors.toList());

		logger.info("completed dao call for the get All user details");
		return list;
	}

	@Override
	public void deleteUser(Integer userId) {
		logger.info("Initiating dao call for the delete the user details with:{}",userId);
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException(AppConstants.NOT_FOUND));
		logger.info("completed dao call for the delete the user details with:{}",userId);
		userRepo.delete(user);

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		//we have encode the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);
	}

//	private User dtoToUser(UserDto userDto) {
//		
//		User user=new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		return user;
//		
//	}
//	
//	public UserDto userToDto(User user) {
//		
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		return userDto;
//		
//	}

}
