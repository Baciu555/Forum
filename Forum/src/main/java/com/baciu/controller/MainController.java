package com.baciu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baciu.entity.Section;
import com.baciu.entity.User;
import com.baciu.service.SectionService;
import com.baciu.service.SessionService;
import com.baciu.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "main")
	public String main(Model model, HttpSession session) {
		User loggedUser = sessionService.getLoggedUser(session);
		if (!(loggedUser == null))
			model.addAttribute("loggedUser", loggedUser);
		
		List<Section> sections = sectionService.getAllSections();
		model.addAttribute("sections", sections);
		
		List<User> bestUsers = userService.getBestUsers();
		model.addAttribute("bestUsers", bestUsers);
		
		return "index";
	}
}
