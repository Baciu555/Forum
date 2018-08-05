package com.baciu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baciu.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	User findByEmail(String email);
	User findByUsernameAndPassword(String username, String password);
	
	@Query("FROM User as u ORDER BY u.commentsCount DESC")
	List<User> getBestUsers();
}
