package com.baciu;

import java.util.Calendar;
import java.util.Date;
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
import com.baciu.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
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
		User user = userService.getById(userId);
		
		Assert.assertNotNull("failure - expected not null", user);
		Assert.assertEquals("failure - expected equals", new Long(userId), user.getId());
		Assert.assertEquals("failure - expected equals", "baciu", user.getUsername());
	}
	
	@Test
	public void testLogIn() {
		Long userId = new Long(1);
		String userName = "baciu";
		String password = "123";
		Exception exception = null;
		
		try {
			User user = userService.logIn(userName, password);
		} catch (Exception e) {
			exception = e;
		}
	}
	
	@Test
	public void testRegister() {
		
	}
	
	@Test
	public void testUpdate() {
		
	}
	
	@Test
	public void testUploadAvatar() {
		
	}

}
