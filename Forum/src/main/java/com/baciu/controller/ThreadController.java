	package com.baciu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baciu.entity.Comment;
import com.baciu.entity.Section;
import com.baciu.entity.Tag;
import com.baciu.entity.Thread;
import com.baciu.entity.User;
import com.baciu.service.CommentService;
import com.baciu.service.SectionService;
import com.baciu.service.SessionService;
import com.baciu.service.TagService;
import com.baciu.service.ThreadService;
import com.baciu.service.UserService;

@Controller
public class ThreadController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ThreadService threadService;
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "thread/{id}", method = RequestMethod.GET)
	public String showThread(Model model, @PathVariable("id") long threadId,
			HttpSession session) {
		User loggedUser = sessionService.getLoggedUser(session);
		if (!(loggedUser == null))
			model.addAttribute("loggedUser", loggedUser);
		
		List<Section> sections = sectionService.getAllSections();
		model.addAttribute("sections", sections);
		
		Thread thread = threadService.getThreadById(threadId);
		if (thread == null)
			return "redirect:/main";
		model.addAttribute("thread", thread);
		
		threadService.addViewCount(threadId);
		Comment comment = new Comment();
		model.addAttribute("comment", comment);
		
		List<User> bestUsers = userService.getBestUsers();
		model.addAttribute("bestUsers", bestUsers);
			
		return "thread";
	}
	
	@RequestMapping(value = "thread/{threadId}", method = RequestMethod.POST)
	public String addComment(@Valid Comment comment, BindingResult bindingResult, 
			@PathVariable("threadId") long threadId, HttpSession session,
			RedirectAttributes redir) {
		
		User loggedUser = sessionService.getLoggedUser(session);
		if (loggedUser == null)
			return "redirect:/main";
		
		if (bindingResult.hasErrors()) {
			redir.addFlashAttribute("contentMessage", bindingResult.getFieldError("content").getDefaultMessage());
			return "redirect:/thread/" + threadId;
		}
		
		commentService.addComment(comment, threadId, loggedUser);

		return "redirect:/thread/" + threadId;
	}
	
	@RequestMapping(value = "addThread/{sectionId}", method=RequestMethod.GET)
	public String showAddThread(Model model, @PathVariable("sectionId") long sectionId,
			HttpSession session) {
		
		User loggedUser = sessionService.getLoggedUser(session);
		if (!(loggedUser == null))
			model.addAttribute("loggedUser", loggedUser);
		
		List<Tag> tags = new ArrayList<Tag>();
		tags = tagService.getAllTags();
		model.addAttribute("tags", tags);
		model.addAttribute("thread", new Thread());
		
		return "add_thread";
	}
	
	@RequestMapping(value = "addThread/{sectionId}", method = RequestMethod.POST)
	public String addThread(@Valid Thread thread, BindingResult bindingResult,
			@PathVariable("sectionId") long sectionId,
			@RequestParam(value = "tagsId", required = false) List<String> tagsId,
			HttpSession session, RedirectAttributes redirectAttributes) {
		
		User loggedUser = sessionService.getLoggedUser(session);
		if (loggedUser == null)
			return "redirect:/main";
		
		thread.setUser(loggedUser);
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute(bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/addThread/" + sectionId;
		}
			
		if (tagsId == null) {
			redirectAttributes.addFlashAttribute("tagsMessage", "Musisz wybrać przynajmniej 1 tag");
			return "redirect:/addThread/" + sectionId;
		}
		
		Section section = new Section();
		section.setId(sectionId);
		thread.setSection(section);
		threadService.addThread(thread, tagsId, sectionId);
		
		return "redirect:/section/" + sectionId;
	}
	
}
