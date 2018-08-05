package com.baciu.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baciu.entity.CurrentUser;
import com.baciu.entity.User;
import com.baciu.service.SectionService;
import com.baciu.service.UserService;

@Controller
public class AccountController {

	@Autowired
	private SectionService sectionService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "user/account", method = RequestMethod.GET)
	public String account(Model model) {
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("loggedUser", userService.getById(currentUser.getId()));
		model.addAttribute("sections", sectionService.getAllSections());
		
		return "account";
	}

	@RequestMapping(value = "user/editData", method = RequestMethod.GET)
	public String showEditData(Model model) {
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("loggedUser", userService.getById(currentUser.getId()));
		model.addAttribute("sections", sectionService.getAllSections());

		return "editData";
	}
	
	@RequestMapping(value = "user/editPassword", method = RequestMethod.GET)
	public String showEditPassword(Model model) {
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("loggedUser", userService.getById(currentUser.getId()));
		model.addAttribute("sections", sectionService.getAllSections());

		return "editPassword";
	}
	
	@RequestMapping(value = "user/editAvatar", method = RequestMethod.GET)
	public String showEditAvatar(Model model) {
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("loggedUser", userService.getById(currentUser.getId()));
		model.addAttribute("sections", sectionService.getAllSections());

		return "editAvatar";
	}

	@RequestMapping(value = "user/editData", method = RequestMethod.POST)
	public String updateData(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute(bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/user/editData";
		}
		
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user.setId(currentUser.getId());

		try {
			userService.updateData(user);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
			return "redirect:/user/editData";
		}

		redirectAttributes.addFlashAttribute("msg", "Dane uzytkownika zmienione");
		return "redirect:/user/editData";
	}
	
	@RequestMapping(value = "user/editPassword", method = RequestMethod.POST)
	public String updatePassword(@RequestParam("password") String password, @RequestParam("passwordConfirm") String passwordConfirm,
			RedirectAttributes redirectAttributes) {
		try {
			userService.updatePassword(password, passwordConfirm);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
			return "redirect:/user/editPassword";
		}

		redirectAttributes.addFlashAttribute("msg", "Hasło zmienione");
		return "redirect:/user/editPassword";
	}
	
	@RequestMapping(value = "user/editAvatar", method = RequestMethod.POST)
	public String updateAvatar(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
		try {
			userService.updateAvatar(file);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("fileErrorMsg", e.getMessage());
			return "redirect:/user/editAvatar";
		}

		redirectAttributes.addFlashAttribute("msg", "Avatar zmieniony");
		return "redirect:/user/editAvatar";
	}

}
