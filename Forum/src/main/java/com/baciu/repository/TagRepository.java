package com.baciu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baciu.entity.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

	Tag findByName(String name);

	@Query("select count(*) from Thread as thr where section.id = :sectionId")
	Long getSectionThreadsNumber(Long sectionId);
}
