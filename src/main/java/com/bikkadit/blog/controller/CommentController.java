package com.bikkadit.blog.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.blog.helper.AppConstants;
import com.bikkadit.blog.payloads.ApiResponse;
import com.bikkadit.blog.payloads.CommentDto;
import com.bikkadit.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	Logger logger=LoggerFactory.getLogger(CommentController.class);

	
	
	/*
	 * @author Godavari
	 * @apiNote This api is used for save the comment
	 * @param postId
	 * @Return
	 */
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment( @Valid @RequestBody CommentDto comment, @PathVariable Integer postId) {
		logger.info("Initiated Request for save Comment");

		CommentDto createComment = this.commentService.createComment(comment, postId);
		logger.info("Request Completed by save comment");
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);

	}
	/*
	 * @apiNote This api is used for deleting the comment
	 * @param commentId
	 * 
	 */

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		logger.info("Initiated Request for delete comment by commentId:{}", commentId);
		this.commentService.deleteComment(commentId);
		logger.info("Completed Request delete comment for commentId:{}", commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.COMMENT_DELETE, true), HttpStatus.OK);

	}

}
