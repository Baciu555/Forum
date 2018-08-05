package com.baciu.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baciu.entity.Section;
import com.baciu.entity.Tag;
import com.baciu.exception.ThreadNotExistsException;
import com.baciu.service.CommentService;
import com.baciu.service.SectionService;
import com.baciu.service.TagService;
import com.baciu.service.ThreadService;

@Controller
public class AdminController {
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private ThreadService threadService;
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String showAdmin(Model model) {
		model.addAttribute("sections", sectionService.getAllSections());
		model.addAttribute("section", new Section());
		model.addAttribute("tag", new Tag());
		
		return "admin";
	}
	
	@RequestMapping(value = "/admin/addSection", method = RequestMethod.POST)
	public String addSection(@Valid Section section, BindingResult bindingResult,
			RedirectAttributes redir) {
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
			RedirectAttributes redir) {
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
	
	@RequestMapping(value = "admin/thread/delete/{threadId}", method = RequestMethod.POST)
	public String deleteThread(@PathVariable("threadId") long threadId, RedirectAttributes redir) {
		Long sectionId = null;
		try {
			sectionId = threadService.deleteThread(threadId);
		} catch (ThreadNotExistsException e) {
			redir.addFlashAttribute("deleteThreadMsg", e.getMessage());
			return "redirect:/thread/" + threadId;
		}
		return "redirect:/section/" + sectionId;
	}
	
	@RequestMapping(value = "admin/comment/delete/{commentId}", method = RequestMethod.POST)
	public String deleteComment(@PathVariable("commentId") long commentId, RedirectAttributes redir) {
		Long threadId = commentService.deleteComment(commentId);
		redir.addFlashAttribute("deleteCommentMsg", "komentarz usunięty");
		return "redirect:/thread/" + threadId;
	}

}
