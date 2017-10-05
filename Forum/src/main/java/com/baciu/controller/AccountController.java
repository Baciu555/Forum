package com.baciu.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baciu.entity.User;
import com.baciu.exception.FileUploadException;
import com.baciu.service.SectionService;
import com.baciu.service.SessionService;
import com.baciu.service.UserService;

@Controller
public class AccountController {

	@Autowired
	private SectionService sectionService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService sessionService;

	@RequestMapping(value = "account")
	public String account(Model model, HttpSession session) {
		User loggedUser = sessionService.getLoggedUser(session);
		if (loggedUser == null)
			return "redirect:/main";

		model.addAttribute("loggedUser", userService.getById(loggedUser.getId()));
		model.addAttribute("sections", sectionService.getAllSections());
		
		return "account";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String show(Model model, HttpSession session) {
		User loggedUser = sessionService.getLoggedUser(session);
		if (loggedUser == null)
			return "redirect:/main";

		model.addAttribute("loggedUser", userService.getById(loggedUser.getId()));
		model.addAttribute("sections", sectionService.getAllSections());

		return "edit";
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String update(@Valid User user, BindingResult bindingResult,
			@RequestParam("passwordConfirm") String passwordConfirm, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, HttpSession session) {
		
		User loggedUser = sessionService.getLoggedUser(session);
		if (loggedUser == null)
			return "redirect:/main";
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute(bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/edit";
		}
		
		user.setId(loggedUser.getId());

		try {
			userService.update(user, passwordConfirm, file);
		} catch (FileUploadException e) {
			redirectAttributes.addFlashAttribute("fileErrorMsg", e.getMessage());
			return "redirect:/edit";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
			return "redirect:/edit";
		}

		redirectAttributes.addFlashAttribute("msg", "Dane uzytkownika zmienione");
		return "redirect:/edit";
	}

}
