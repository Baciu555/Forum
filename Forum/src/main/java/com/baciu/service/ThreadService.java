package com.baciu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baciu.DAO.SectionDAO;
import com.baciu.DAO.ThreadDAO;
import com.baciu.entity.Section;
import com.baciu.entity.Tag;
import com.baciu.entity.Thread;

@Service
public class ThreadService {
	
	@Autowired
	private ThreadDAO threadDAO;
	
	@Autowired
	private SectionDAO sectionDAO;
	
	public List<Thread> getSectionThreads(long sectionId, int page) {
		int maxResults = 10;
		int firstResult = (page - 1) * 10;
		List<Thread> threads = threadDAO.getSectionThreads(sectionId, firstResult, maxResults);
		return threads;
	}
	
	public long getSectionPages(long sectionId) {
		long threadsNumber = threadDAO.getSectionThreadsNumber(sectionId);
		long pages = threadsNumber / 10;
		return pages;
	}
	
	public List<List<Thread>> getSectionsThreads(int maxResults) {
		List<List<Thread>> threads = new ArrayList<List<Thread>>();
		List<Thread> sectionThreads = new ArrayList<>();
		List<Section> sections = sectionDAO.getAllSections();
		int firstResult = 0;
		
		for (Section section : sections) {
			sectionThreads = threadDAO.getSectionThreads(section.getId(), firstResult, maxResults);
			if (sectionThreads.isEmpty()) {
				sectionThreads.add(createThreadForEmptySection(section));
			}
			threads.add(sectionThreads);
		}
		
		return threads;
	}
	
	private Thread createThreadForEmptySection(Section section) {
		Section sctn = new Section();
		sctn.setId(section.getId());
		sctn.setName(section.getName());
		Thread thread = new Thread();
		thread.setSection(sctn);
		return thread;
	}
	
	public void addThread(Thread thread, List<String> tagsId, long sectionId) {
		Set<Tag> tags = new HashSet<Tag>();
		
		for (String tagId : tagsId) {
			Tag tag = new Tag();
			tag.setId(Long.parseLong(tagId));
			tags.add(tag);
		}
		
		Section section = new Section();
		section.setId(sectionId);
		thread.setSection(section);
		
		thread.setEntryDate(new Date());
		thread.setTags(tags);
		thread.setViewsCount(0l);
		
		try {
			threadDAO.addThread(thread);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addViewCount(long threadId) {
		Thread thread = new Thread();
		try {
			thread = threadDAO.getThread(threadId);
			thread.setViewsCount(thread.getViewsCount() + 1);
			threadDAO.addViewCount(thread);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Thread getThreadById(long threadId) {
		Thread thread = new Thread();
		try {
			thread = threadDAO.getThread(threadId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return thread;
	}
	
	public void deleteThread(long threadId) {
		Thread thread = threadDAO.getThread(threadId);
		threadDAO.deleteThread(thread);
	}

}
