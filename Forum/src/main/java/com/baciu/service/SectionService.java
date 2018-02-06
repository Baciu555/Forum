package com.baciu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baciu.entity.Section;
import com.baciu.repository.SectionRepository;

@Service
public class SectionService {
	
	@Autowired
	private SectionRepository sectionRepository;

	public List<Section> getAllSections() {
		List<Section> sections = (List<Section>) sectionRepository.findAll();

		return sections;
	}

	public Section getSectionById(long sectionId) {
		Section section = sectionRepository.findOne(sectionId);
		
		return section;
	}
	
	public boolean addSection(Section section) {
		if (sectionRepository.findByName(section.getName()) != null)
			return false;
		
		sectionRepository.save(section);
		return true;
	}

}
