package com.baciu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baciu.entity.Section;
import com.baciu.service.SectionService;

@Controller
public class SearchController {
	
	@Autowired
	private SectionService sectionService;
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String showSearchPage(Model model) {
		List<Section> sections = sectionService.getAllSections();
		model.addAttribute("sections", sections);
		return "search";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String search(@RequestParam("search") String search) {
		return "search";
	}

}