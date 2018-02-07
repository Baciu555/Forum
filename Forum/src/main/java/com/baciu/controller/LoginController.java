package com.baciu.controller;

import java.util.InputMismatchException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baciu.entity.Section;
import com.baciu.entity.User;
import com.baciu.exception.AccountBannedException;
import com.baciu.exception.EmailExistsException;
import com.baciu.exception.UserNotExistsException;
import com.baciu.exception.UsernameExistsException;
import com.baciu.service.SectionService;
import com.baciu.service.SessionService;
import com.baciu.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private SectionService sectionService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String showLoginPage(Model model, HttpSession session) {
		User loggedUser = sessionService.getLoggedUser(session);
		if (!(loggedUser == null))
			return "redirect:/main";

		List<Section> sections = sectionService.getAllSections();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("sections", sections);
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, HttpSession session, Model model) {
		User loggedUser = new User();
		System.out.println(user);

		try {
			loggedUser = userService.logIn(user.getUsername(), user.getPassword());
		} catch (UserNotExistsException e) {
			model.addAttribute("loginMsg", "Niepoprawny login lub haslo");
			return "login";
		} catch (AccountBannedException e) {
			model.addAttribute("loginMsg", e.getMessage());
			return "login";
		}
		
		session.setAttribute("userId", loggedUser.getId());
		return "redirect:/main";
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
	public String logout(HttpSession session) {
		session.removeAttribute("userId");
		return "redirect:main";
	}
}
