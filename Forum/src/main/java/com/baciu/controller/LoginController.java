package com.baciu.controller;

import java.util.InputMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baciu.entity.User;
import com.baciu.exception.EmailExistsException;
import com.baciu.exception.UsernameExistsException;
import com.baciu.service.SectionService;
import com.baciu.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private SectionService sectionService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null)
			model.addAttribute("loginMsg", "Niepoprawny login lub hasło");
		model.addAttribute("sections", sectionService.getAllSections());
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(@Valid User user, BindingResult bindingResult,
			@RequestParam("passwordConf") String passwordConf, Model model) {

		if (bindingResult.hasErrors()) {
			return "login";
		}

		try {
			userService.register(user, passwordConf);
		} catch (UsernameExistsException usernameExistsException) {
			model.addAttribute("usernameMsg", usernameExistsException.getMessage());
			return "login";
		} catch (InputMismatchException inputMismatchException) {
			model.addAttribute("passwordMsg", inputMismatchException.getMessage());
			return "login";
		} catch (EmailExistsException emailExistsException) {
			model.addAttribute("emailMsg", emailExistsException.getMessage());
			return "login";
		}

		model.addAttribute("registerMsg", "Rejestracja udana");

		return "login";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null)
			new SecurityContextLogoutHandler().logout(request, response, auth);
		return "redirect:main";
	}
}
