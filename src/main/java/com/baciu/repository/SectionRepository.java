package com.baciu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baciu.entity.Section;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {

	Section findByName(String name);
}
