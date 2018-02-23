package com.baciu.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	public List<Thread> getSectionThreads(long sectionId, int pageNumber) {
		int maxResults = 10;
		Pageable pageable = new PageRequest(pageNumber - 1, maxResults, Sort.Direction.DESC, "entryDate");
		Page<Thread> page = threadRepository.getThreads(sectionId, pageable);
			
		return page.getContent();
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
		thread.setTags(tags);
		
		threadRepository.save(thread);
	}
	
	public void addViewCount(long threadId) {
		Thread thread = new Thread();
		thread = threadRepository.findOne(threadId);
		thread.setViewsCount(thread.getViewsCount() + 1);
		threadRepository.save(thread);
	}
	
	public Thread getThreadById(long threadId) {
		return threadRepository.findOne(threadId);
	}
	
	public Long deleteThread(long threadId) throws ThreadNotExistsException {
		Thread thread = threadRepository.findOne(threadId);
		if (thread == null)
			throw new ThreadNotExistsException("Problem przy usuwaniu wątku. Spróbuj ponownie");
		threadRepository.delete(thread);
		return thread.getSection().getId();
	}
	
	public List<Thread> getSearchedThreads(String searchedText) {
		List<Thread> threadsByContent = threadRepository.findByContentContaining(searchedText);
		List<Thread> threadsBySubject = threadRepository.findBySubjectContaining(searchedText);
		
		List<Thread> threads = threadsByContent;
		for (Thread t : threadsBySubject)
			if (!threads.contains(t))
				threads.add(t);
		
		return threads;
	}

}
