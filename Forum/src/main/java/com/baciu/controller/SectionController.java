package com.baciu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baciu.entity.CurrentUser;
import com.baciu.entity.Section;
import com.baciu.entity.Thread;
import com.baciu.service.SectionService;
import com.baciu.service.ThreadService;
import com.baciu.service.UserService;

@Controller
public class SectionController {
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private ThreadService threadService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "section/{sectionId}", method = RequestMethod.GET)
	public String showSection(@PathVariable("sectionId") long sectionId, Model model) {
		Section section = sectionService.getSectionById(sectionId);
		if (section == null) return "redirect:/main";
		model.addAttribute("section", section);
		model.addAttribute("sections", sectionService.getAllSections());
		model.addAttribute("threads", threadService.getSectionThreads(sectionId, 1));
		model.addAttribute("bestUsers", userService.getBestUsers());
		model.addAttribute("pages", threadService.getSectionPages(sectionId));
		int currentPage = 1;
		model.addAttribute("currentPage", currentPage);
		
		return "section";
	}
	
	@RequestMapping(value = "section/{sectionId}/{page}", method = RequestMethod.GET)
	public String showSectionById(@PathVariable("sectionId") long sectionId, 
			@PathVariable("page") int page, Model model) {
		
		Section section = sectionService.getSectionById(sectionId);
		if (section == null) return "redirect:/main";
		model.addAttribute("section", section);
		
		List<Thread> threads = threadService.getSectionThreads(sectionId, page);
		if (threads.isEmpty()) return "redirect:/main";
		model.addAttribute("threads", threads);
		model.addAttribute("pages", threadService.getSectionPages(sectionId));
		model.addAttribute("currentPage", page);
		model.addAttribute("sections", sectionService.getAllSections());
		
		return "section";
	}
}
