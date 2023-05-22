package com.bikkadit.blog.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikkadit.blog.helper.AppConstants;
import com.bikkadit.blog.payloads.ApiResponse;
import com.bikkadit.blog.payloads.CategoryDto;
import com.bikkadit.blog.payloads.PostDto;
import com.bikkadit.blog.payloads.PostResponse;
import com.bikkadit.blog.services.FileService;
import com.bikkadit.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	FileService fileService;

	@Value("/$project.image")
	private String path;

	Logger logger = LoggerFactory.getLogger(PostController.class);

	/*
	 * @author Godavari
	 * 
	 * @apiNote this api is used for create a post
	 * 
	 * @param postDto
	 * 
	 * @param userId
	 * 
	 * @param categoryId
	 * 
	 * @return
	 */

	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		logger.info("Request entering for create post");

		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		logger.info("Completed Request for create post");

		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	// get by user

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {

		logger.info("Initiated Request for get the single user details with userId:{}", userId);
		List<PostDto> posts = this.postService.getPostsByUser(userId);

		logger.info("Completed Request for get the single user details with userId:{}", userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	// get By category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {

		logger.info("Initiated Request for get the single category details with categoryId:{}", categoryId);
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);

		logger.info("completed Request for get the single category details with categoryId:{}", categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	/*
	 * apiNote This api is used by get the All post
	 * 
	 * @Return
	 */
	// get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		logger.info("Initiated Request for get the All post details");

		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);

		logger.info("Request completed by get the All post details");
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);

	}

	/*
	 * @apiNote This api is used to get post
	 * 
	 * @param postId
	 * 
	 * @Return
	 */

	// get post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

		logger.info("Initiated Request for get the single post details with postId:{}", postId);

		PostDto postDto = this.postService.getPostById(postId);

		logger.info("Completed Request for get the single post details with postId:{}", postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);

	}

	/*
	 * @author Godavari
	 * 
	 * @apiNote This api is used for delete the post
	 * 
	 * @param postId
	 * 
	 * @Return
	 *
	 */

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {

		logger.info("Initiated Request for delete post details with postId:{}", postId);
		this.postService.deletePost(postId);

		logger.info("Completed Request for delete post detils with postId:{}", postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.POST_DELETE, true), HttpStatus.OK);

	}

	/*
	 * @apiNote This api is used for update the post
	 * 
	 * @param postDto
	 * 
	 * @param postId
	 * 
	 * @Return
	 */

	@PutMapping("/posts/{postId}")

	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {

		logger.info("Initiated Request for update the post by postId:{}", postId);

		PostDto updatePost = this.postService.updatePost(postDto, postId);

		logger.info("Completed Request for delete post details with postId:{}", postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	// search
//	@GetMapping("/posts/search/{keywords}")
//	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
//		List<PostDto> result = this.postService.searchPosts(keywords);
//		
//		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
//		
//	}

	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {

		logger.info("Initiated Request for upload the image");

		String fileName = this.fileService.uploadImage(path, image);

		PostDto postDto = this.postService.getPostById(postId);

		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);

		logger.info("Completed the post for upload the image");
		return new ResponseEntity<PostDto>(HttpStatus.OK);
	}

}
