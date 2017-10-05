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
import com.baciu.entity.Tag;
import com.baciu.entity.User;
import com.baciu.service.SectionService;
import com.baciu.service.SessionService;
import com.baciu.service.TagService;

@Controller
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value = "tag/{tagId}", method = RequestMethod.GET)
	public String showTag(@PathVariable("tagId") long tagId, Model model,
			HttpSession session) {
		
		User loggedUser = sessionService.getLoggedUser(session);
		if (!(loggedUser == null))
			model.addAttribute("loggedUser", loggedUser);
		
		List<Section> sections = sectionService.getAllSections();
		model.addAttribute("sections", sections);
		
		Tag tag = tagService.getById(tagId);
		if (tag == null)
			return "redirect:/main";
		
		model.addAttribute("tag", tag);
		
		return "tags";
	}

}
