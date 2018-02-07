package com.baciu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baciu.entity.CurrentUser;
import com.baciu.service.SectionService;
import com.baciu.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "main")
	public String main(Model model) {
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("loggedUser", userService.getById(currentUser.getId()));
		model.addAttribute("sections", sectionService.getAllSections());
		model.addAttribute("bestUsers", userService.getBestUsers());
		
		return "index";
	}
}
