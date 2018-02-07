	package com.baciu.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baciu.entity.Comment;
import com.baciu.entity.CurrentUser;
import com.baciu.entity.Section;
import com.baciu.entity.Thread;
import com.baciu.entity.User;
import com.baciu.service.CommentService;
import com.baciu.service.SectionService;
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
	private UserService userService;

	@RequestMapping(value = "thread/{id}", method = RequestMethod.GET)
	public String showThread(Model model, @PathVariable("id") long threadId) {
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("loggedUser", userService.getById(currentUser.getId()));
		model.addAttribute("sections", sectionService.getAllSections());
		
		Thread thread = threadService.getThreadById(threadId);
		if (thread == null) return "redirect:/main";
		model.addAttribute("thread", thread);
		
		threadService.addViewCount(threadId);
		Comment comment = new Comment();
		model.addAttribute("comment", comment);
		model.addAttribute("bestUsers", userService.getBestUsers());
			
		return "thread";
	}
	
	@RequestMapping(value = "thread/{threadId}", method = RequestMethod.POST)
	public String addComment(@Valid Comment comment, BindingResult bindingResult, 
			@PathVariable("threadId") long threadId, RedirectAttributes redir) {
		if (bindingResult.hasErrors()) {
			redir.addFlashAttribute("contentMessage", bindingResult.getFieldError("content").getDefaultMessage());
			return "redirect:/thread/" + threadId;
		}
		
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		commentService.addComment(comment, threadId, currentUser);

		return "redirect:/thread/" + threadId;
	}
	
	@RequestMapping(value = "addThread/{sectionId}", method=RequestMethod.GET)
	public String showAddThread(Model model, @PathVariable("sectionId") long sectionId) {
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("loggedUser", userService.getById(currentUser.getId()));
		model.addAttribute("tags", tagService.getAllTags());
		model.addAttribute("thread", new Thread());
		
		return "add_thread";
	}
	
	@RequestMapping(value = "addThread/{sectionId}", method = RequestMethod.POST)
	public String addThread(@Valid Thread thread, BindingResult bindingResult,
			@PathVariable("sectionId") long sectionId,
			@RequestParam(value = "tagsId", required = false) List<String> tagsId,
			RedirectAttributes redirectAttributes) {
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getById(currentUser.getId());
		thread.setUser(user);
		
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
