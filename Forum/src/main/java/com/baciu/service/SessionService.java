package com.baciu.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baciu.entity.User;

@Service
public class SessionService {
	
	@Autowired
	private UserService userService;
	
	public User getLoggedUser(HttpSession session) {
		if (session.getAttribute("userId") == null)
			return null;
		
		long userId = Long.parseLong(session.getAttribute("userId").toString());
		User user = userService.getById(userId);
		return user;
	}

}
