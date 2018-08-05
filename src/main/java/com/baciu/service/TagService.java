package com.baciu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baciu.entity.Tag;
import com.baciu.repository.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	public List<Tag> getAllTags() {
		return (List<Tag>) tagRepository.findAll();
	}
	
	public Tag getById(long tagId) {
		Tag tag = tagRepository.findOne(tagId);
		return tag;
	}
	
	public boolean addTag(Tag tag) {
		if (tagRepository.findByName(tag.getName()) != null)
			return false;
		
		tagRepository.save(tag);
		return true;
	}

}
