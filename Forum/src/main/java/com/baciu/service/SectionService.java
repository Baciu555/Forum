package com.baciu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baciu.DAO.SectionDAO;
import com.baciu.entity.Section;

@Service
public class SectionService {

	@Autowired
	private SectionDAO sectionDAO;

	public List<Section> getAllSections() {
		List<Section> sections = sectionDAO.getAllSections();

		return sections;
	}

	public Section getSectionById(long sectionId) {
		Section section = new Section();
		section = sectionDAO.getById(sectionId);
		
		return section;
	}
	
	public boolean addSection(Section section) {
		if (sectionDAO.sectionExists(section.getName()))
			return false;
		
		sectionDAO.addSection(section);
		return true;
	}

}
