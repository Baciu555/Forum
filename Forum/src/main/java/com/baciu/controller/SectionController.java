package com.baciu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baciu.entity.Section;
import com.baciu.entity.Thread;
import com.baciu.entity.User;
import com.baciu.service.SectionService;
import com.baciu.service.SessionService;
import com.baciu.service.ThreadService;
import com.baciu.service.UserService;

@Controller
public class SectionController {
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private ThreadService threadService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "section/{sectionId}", method = RequestMethod.GET)
	public String showSection(@PathVariable("sectionId") long sectionId, 
			Model model, HttpSession session) {
		
		User loggedUser = sessionService.getLoggedUser(session);
		if (!(loggedUser == null))
			model.addAttribute("loggedUser", loggedUser);
		
		List<Section> sections = sectionService.getAllSections();
		model.addAttribute("sections", sections);
		
		Section section = sectionService.getSectionById(sectionId);
		if (section == null)
			return "redirect:/main";
		model.addAttribute("section", section);
		
		List<Thread> threads = threadService.getSectionThreads(sectionId, 1);
		model.addAttribute("threads", threads);
		
		List<User> bestUsers = userService.getBestUsers();
		model.addAttribute("bestUsers", bestUsers);
		
		long pages = threadService.getSectionPages(sectionId);
		model.addAttribute("pages", pages);
		
		model.addAttribute("currentPage", 1);
		
		return "section";
	}
	
	@RequestMapping(value = "section/{sectionId}/{page}", method = RequestMethod.GET)
	public String showSectionById(@PathVariable("sectionId") long sectionId, 
			@PathVariable("page") int page, Model model, HttpSession session) {
		
		User loggedUser = sessionService.getLoggedUser(session);
		if (!(loggedUser == null))
			model.addAttribute("loggedUser", loggedUser);
		
		List<Section> sections = sectionService.getAllSections();
		model.addAttribute("sections", sections);
		
		Section section = sectionService.getSectionById(sectionId);
		if (section == null)
			return "redirect:/main";
		model.addAttribute("section", section);
		
		List<Thread> threads = threadService.getSectionThreads(sectionId, page);
		if (threads.isEmpty())
			return "redirect:/main";
		model.addAttribute("threads", threads);
		
		long pages = threadService.getSectionPages(sectionId);
		model.addAttribute("pages", pages);
		
		model.addAttribute("currentPage", page);
		
		return "section";
	}
}
