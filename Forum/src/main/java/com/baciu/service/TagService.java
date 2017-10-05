package com.baciu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baciu.DAO.TagDAO;
import com.baciu.entity.Tag;

@Service
public class TagService {
	
	@Autowired
	private TagDAO tagDAO;
	
	public List<Tag> getAllTags() {
		List<Tag> tags = new ArrayList<Tag>();
		try {
			tags = tagDAO.getAllTags();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	public Tag getById(long tagId) {
		Tag tag = new Tag();
		try {
			tag = tagDAO.getById(tagId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tag;
	}
	
	public boolean addTag(Tag tag) {
		if (tagDAO.tagExists(tag.getName()))
			return false;
		
		tagDAO.addTag(tag);
		return true;
	}

}
