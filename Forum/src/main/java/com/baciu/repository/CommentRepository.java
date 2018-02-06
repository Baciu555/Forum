package com.baciu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baciu.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

}
