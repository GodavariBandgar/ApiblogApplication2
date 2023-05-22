package com.bikkadit.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.blog.entities.Comment;
import com.bikkadit.blog.entities.Post;
import com.bikkadit.blog.exceptions.ResourceNotFoundException;
import com.bikkadit.blog.payloads.CommentDto;
import com.bikkadit.blog.repository.CommentRepo;
import com.bikkadit.blog.repository.PostRepository;
import com.bikkadit.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;
	Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {

		logger.info("Initiating dao call for the comment details");
		Post post = this.postRepo.findById(postId)

				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);

		comment.setPost(post);

		Comment saveComment = this.commentRepo.save(comment);

		logger.info("Completed dao for the save comment details");
		return this.modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		logger.info("Initiating dao call for the delete the comment details with:{}", commentId);

		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
		logger.info("Completed dao call for the delete the comment details with:{}", commentId);

		this.commentRepo.delete(com);
	}

}
