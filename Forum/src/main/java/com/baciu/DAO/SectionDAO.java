package com.baciu.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.baciu.entity.Section;

@Transactional
@Repository
public class SectionDAO {
	
	@PersistenceContext
	private EntityManager em;

	public List<Section> getAllSections() {
		String hql = "from Section as sctn";
		List<Section> sections = new ArrayList<Section>();
		
		sections = em.createQuery(hql, Section.class).getResultList();
		
		return sections;
	}
	
	public Section getById(long sectionId) {
		return em.find(Section.class, sectionId);
	}
	
	public List<Section> getSections(int threadQuantity, int limit) {
		String hql = "from Section as sctn "
				+ "inner join sctn.threads as threads "
				+ "order by threads.entryDate";
		List<Section> sections = new ArrayList<Section>();
		
		sections = em.createQuery(hql, Section.class).getResultList();
		
		return sections;
	}
	
	public Section getSectionThreads(long sectionId, int page) {
		Section section = new Section();
		
		section = em.find(Section.class, sectionId);
		return section;
	}
	
	public void addSection(Section section) {
		em.persist(section);
	}	
	
	public boolean sectionExists(String name) {
		String hql = "FROM Section as sctn WHERE sctn.name = :name";
		try {
			em.createQuery(hql, Section.class).setParameter("name", name).getSingleResult();
		} catch (NoResultException noResultExeption) {
			return false;
		}
		return true;
	}
}
