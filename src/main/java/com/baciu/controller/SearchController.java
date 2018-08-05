package com.baciu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baciu.service.SectionService;
import com.baciu.service.ThreadService;
import com.baciu.entity.Thread;

@Controller
public class SearchController {
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private ThreadService threadService;
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String showSearchPage(@RequestParam(value = "search", required = false) String searchedText, Model model) {
		if (searchedText != null) {
			List<Thread> threads = threadService.getSearchedThreads(searchedText);
			model.addAttribute("threads", threads);
		}
		
		model.addAttribute("sections", sectionService.getAllSections());
		return "search";
	}

}