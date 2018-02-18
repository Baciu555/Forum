package com.baciu.services;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baciu.entity.Comment;
import com.baciu.repository.CommentRepository;
import com.baciu.service.CommentService;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentServiceTests {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Before
	public void setUp() {}
	
	@After
	public void tearDown() {}
	
	@Test
	public void testAddComment() {		
	}
	
	@Test
	public void testDeleteComment() {
		Long commentId = new Long(1);
		commentService.deleteComment(commentId);
		Comment comment = commentRepository.findOne(commentId);
		
		Assert.assertNull("failure - expected null", comment);
		
	}

}
