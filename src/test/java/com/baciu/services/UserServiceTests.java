package com.baciu.services;

import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baciu.entity.User;
import com.baciu.exception.EmailExistsException;
import com.baciu.exception.UsernameExistsException;
import com.baciu.repository.UserRepository;
import com.baciu.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {}
	
	@Test
	public void testBanUser() {
		Exception exception = null;
		long userId = 1;
		Date date = new Date();
		// set tomorrow date
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		
		try {
			userService.banUser(userId, date);
		} catch (Exception e) {
			exception = e;
		}
		
		User user = userService.getById(userId);
		
		Assert.assertNotNull("failure - expected not null", user.getBanExpire());
		Assert.assertEquals("failure - excepted equal values", new Integer(1), user.getBanCount());
		Assert.assertEquals("failure - excepted equal values", date, user.getBanExpire());
		Assert.assertNull("failure - expected null", exception);
	}
	
	@Test
	public void testGetBestUsers() {
		List<User> bestUsers = userService.getBestUsers();
		
		Assert.assertNotNull("failure - expected not null", bestUsers);
		Assert.assertEquals("failure - expected equal size", 5, bestUsers.size());
	}
	
	@Test
	public void testGetById() {
		Long userId = new Long(1);
		String userName = "user";
		User user = userService.getById(userId);
		
		Assert.assertNotNull("failure - expected not null", user);
		Assert.assertEquals("failure - expected equals", new Long(userId), user.getId());
		Assert.assertEquals("failure - expected equals", userName, user.getUsername());
	}
	
	@Test
	public void testRegister() {
		User user = new User();
		user.setUsername("username123");
		user.setPassword("password123");
		String passwordConfirm = "password123";
		user.setEmail("email123@gmail.com");
		Exception exception = null;
		
		try {
			userService.register(user, passwordConfirm);
		} catch (InputMismatchException | EmailExistsException | UsernameExistsException e) {
			exception = e;
		}
		
		User newUser = userRepository.findByUsername(user.getUsername());
		
		Assert.assertEquals("failure - expected equal values", user.getUsername(), newUser.getUsername());
		Assert.assertEquals("failure - expected equal values", user.getPassword(), newUser.getPassword());
		Assert.assertEquals("failure - expected equal values", user.getEmail(), newUser.getEmail());
		Assert.assertNull("failure - expected null", exception);
	}

}
