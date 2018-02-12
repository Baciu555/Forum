package com.baciu.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baciu.entity.Section;
import com.baciu.entity.Tag;
import com.baciu.entity.Thread;
import com.baciu.exception.ThreadNotExistsException;
import com.baciu.repository.ThreadRepository;

@Service
public class ThreadService {
	
	@Autowired
	private ThreadRepository threadRepository;
	
	public List<Thread> getSectionThreads(long sectionId, int page) {
		int maxResults = 10;
		int firstResult = (page - 1) * 10;
		List<Thread> threads = threadRepository.getSectionThreads(sectionId);
		if (threads.size() < maxResults)
			threads = threads.subList(firstResult, threads.size());
		else
			threads = threads.subList(firstResult, maxResults);
		return threads;
	}
	
	public long getSectionPages(long sectionId) {
		long threadsNumber = threadRepository.getSectionThreadsCount(sectionId);
		long pages = threadsNumber / 10;
		return pages;
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
		
		threadRepository.save(thread);
	}
	
	public void addViewCount(long threadId) {
		Thread thread = new Thread();
		thread = threadRepository.findOne(threadId);
		thread.setViewsCount(thread.getViewsCount() + 1);
		threadRepository.save(thread);
	}
	
	public Thread getThreadById(long threadId) {
		Thread thread = threadRepository.findOne(threadId);
		return thread;
	}
	
	public Long deleteThread(long threadId) throws ThreadNotExistsException {
		Thread thread = threadRepository.findOne(threadId);
		if (thread == null)
			throw new ThreadNotExistsException("Problem przy usuwaniu wątku. Spróbuj ponownie");
		threadRepository.delete(thread);
		return thread.getSection().getId();
	}

}
