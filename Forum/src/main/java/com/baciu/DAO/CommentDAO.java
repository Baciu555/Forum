package com.baciu.DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.baciu.entity.Comment;

@Transactional
@Repository
public class CommentDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public void addComment(Comment comment) {
		em.persist(comment);
	}
	
	public Comment getById(long commentId) throws NoResultException {
		Comment comment = em.find(Comment.class, commentId);
		return comment;
	}
	
	public void deleteComment(Comment comment) {
		em.remove(comment);
	}

}
