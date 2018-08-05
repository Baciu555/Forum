package com.baciu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baciu.entity.Tag;
import com.baciu.service.SectionService;
import com.baciu.service.TagService;
import com.baciu.service.UserService;

@Controller
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "tag/{tagId}", method = RequestMethod.GET)
	public String showTag(@PathVariable("tagId") long tagId, Model model) {
		Tag tag = tagService.getById(tagId);
		if (tag == null) return "redirect:/main";
		
		model.addAttribute("tag", tag);
		model.addAttribute("sections", sectionService.getAllSections());
		model.addAttribute("bestUsers", userService.getBestUsers());
		
		return "tags";
	}

}
