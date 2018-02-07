package com.baciu.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baciu.entity.User;
import com.baciu.service.SectionService;
import com.baciu.service.UserService;

@Controller
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SectionService sectionService;

	@RequestMapping(value = "profile/{id}", method = RequestMethod.GET)
	public String showProfile(@PathVariable("id") Long id, Model model) {
		User user = userService.getById(id);
		if (user == null) return "redirect:/main";
		
		model.addAttribute("user", user);
		model.addAttribute("sections", sectionService.getAllSections());
		
		return "profile";
	}
	
	@RequestMapping(value = "admin/profile/{id}", method = RequestMethod.POST)
	public String banUser(@PathVariable("id") Long id, @RequestParam("date") @DateTimeFormat(iso = ISO.DATE) Date date,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			userService.banUser(id, date);
		} catch (NullPointerException e) {
			redirectAttributes.addFlashAttribute("banMessage", "Wybierz date");
			return "redirect:/profile/" + id;
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("banMessage", e.getMessage());
			return "redirect:/profile/" + id;
		}
		
		redirectAttributes.addFlashAttribute("banMessage", "Użytkownik zbanowany");
		return "redirect:/profile/" + id;
	}
}
