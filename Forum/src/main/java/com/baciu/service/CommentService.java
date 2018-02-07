package com.baciu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baciu.entity.Comment;
import com.baciu.entity.CurrentUser;
import com.baciu.entity.Thread;
import com.baciu.entity.User;
import com.baciu.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	public void addComment(Comment comment, long threadId, CurrentUser loggedUser) {
		User user = new User();
		user.setId(loggedUser.getId());
		comment.setUser(user);
		
		Thread thread = new Thread();
		thread.setId(threadId);
		comment.setThread(thread);
		
		commentRepository.save(comment);
	}
	
	public void deleteComment(long commentId) {
		Comment comment = commentRepository.findOne(commentId);
		commentRepository.delete(comment);
	}

}
