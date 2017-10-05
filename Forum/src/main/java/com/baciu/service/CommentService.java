package com.baciu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baciu.DAO.CommentDAO;
import com.baciu.entity.Comment;
import com.baciu.entity.Thread;
import com.baciu.entity.User;

@Service
public class CommentService {
	
	@Autowired
	private CommentDAO commentDAO;
	
	public void addComment(Comment comment, long threadId, User loggedUser) {
		User user = new User();
		user.setId(loggedUser.getId());
		comment.setUser(user);
		
		Thread thread = new Thread();
		thread.setId(threadId);
		comment.setThread(thread);
		
		try {
			commentDAO.addComment(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteComment(long commentId) {
		Comment comment = commentDAO.getById(commentId);
		commentDAO.deleteComment(comment);
	}

}
