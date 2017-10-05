package com.baciu.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.baciu.entity.Tag;

@Transactional
@Repository
public class TagDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Tag> getAllTags() {
		String hql = "FROM Tag as tg";
		List<Tag> tags = new ArrayList<Tag>();
		tags = em.createQuery(hql, Tag.class).getResultList();
		
		return tags;
	}
	
	public Tag getById(long tagId) {
		Tag tag = new Tag();
		tag = em.find(Tag.class, tagId);
		
		return tag;
	}
	
	public void addTag(Tag tag) {
		em.persist(tag);
	}
	
	public boolean tagExists(String name) {
		String hql = "FROM Tag as tg WHERE tg.name = :name";
		try {
			em.createQuery(hql, Tag.class).setParameter("name", name).getSingleResult();
		} catch (NoResultException noResultExeption) {
			return false;
		}
		return true;
	}

}
