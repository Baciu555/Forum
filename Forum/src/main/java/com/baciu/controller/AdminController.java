package com.baciu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baciu.entity.Section;
import com.baciu.entity.Tag;
import com.baciu.entity.User;
import com.baciu.service.SectionService;
import com.baciu.service.SessionService;
import com.baciu.service.TagService;

@Controller
public class AdminController {
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private TagService tagService;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String showAdmin(Model model, HttpSession session) {
		User loggedUser = sessionService.getLoggedUser(session);
		if (!(loggedUser == null) && loggedUser.getPermission().equals("admin"))
			model.addAttribute("loggedUser", loggedUser);
		else
			return "redirect:/main";
		
		List<Section> sections = sectionService.getAllSections();
		model.addAttribute("sections", sections);
		model.addAttribute("section", new Section());
		model.addAttribute("tag", new Tag());
		
		return "admin";
	}
	
	@RequestMapping(value = "/admin/addSection", method = RequestMethod.POST)
	public String addSection(@Valid Section section, BindingResult bindingResult,
			RedirectAttributes redir, HttpSession session) {
		
		User loggedUser = sessionService.getLoggedUser(session);
		if (loggedUser == null || !loggedUser.getPermission().equals("admin"))
			return "redirect:/main";
		
		if (bindingResult.hasErrors()) {
			redir.addFlashAttribute("sectionError", bindingResult.getFieldError("name").getDefaultMessage());
			return "redirect:/admin";
		}
		
		if (sectionService.addSection(section)) {
			redir.addFlashAttribute("sectionMsg", "Dodano sekcje");
			return "redirect:/admin";
		}
		
		redir.addFlashAttribute("sectionError", "Sekcja o tej nazwie już istnieje");
		return "redirect:/admin";
	}
	
	@RequestMapping(value = "/admin/addTag", method = RequestMethod.POST)
	public String addTag(@Valid Tag tag, BindingResult bindingResult,
			RedirectAttributes redir, HttpSession session) {
		
		User loggedUser = sessionService.getLoggedUser(session);
		if (loggedUser == null || !loggedUser.getPermission().equals("admin"))
			return "redirect:/main";
		
		if (bindingResult.hasErrors()) {
			redir.addFlashAttribute("tagError", bindingResult.getFieldError("name").getDefaultMessage());
			return "redirect:/admin";
		}
		
		if (tagService.addTag(tag)) {
			redir.addFlashAttribute("tagMsg", "Dodano tag");
			return "redirect:/admin";
		}
		
		redir.addFlashAttribute("tagError", "Tag o tej nazwie już istnieje");
		return "redirect:/admin";
	}

}
