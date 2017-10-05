package com.baciu.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.baciu.entity.Thread;

@Transactional
@Repository
public class ThreadDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public void addThread(Thread thread) {
		em.persist(thread);
	}
	
	public void addViewCount(Thread thread) {
		em.persist(thread);
	}
	
	public Thread getThread(long threadId) {
		Thread thread = new Thread();
		thread = em.find(Thread.class, threadId);
		
		return thread;
	}
	
	public List<Thread> getSectionThreads(long sectionId, int firstResult, int maxResults) {
		String hql = "from Thread as thr "
				+ "where section.id = :sectionId "
				+ "order by thr.entryDate desc";
		
		List<Thread> threads = new ArrayList<Thread>();
		threads = em.createQuery(hql, Thread.class).setParameter("sectionId", sectionId).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
		
		return threads;
	}
	
	public List<Thread> getSectionsThreads(int maxResults) {
		String hql = "from Thread as thr "
				+ "order by thr.entryDate desc";
		
		List<Thread> threads = new ArrayList<Thread>();
		threads = em.createQuery(hql, Thread.class).setMaxResults(maxResults).getResultList();
		
		return threads;
	}
	
	public long getSectionThreadsNumber(long sectionId) {
		String hql = "select count(*) from Thread as thr "
				+ "where section.id = :sectionId";
		
		long threadsNumber = 0l;
		threadsNumber = em.createQuery(hql, Long.class).setParameter("sectionId", sectionId)
				.getSingleResult();
		
		return threadsNumber;
	}
	
	public void deleteThread(Thread thread) {
		em.remove(thread);
	}
}
