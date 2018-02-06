package com.baciu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baciu.entity.Thread;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {
	
	@Query("from Thread as thr where section.id = ? order by thr.entryDate desc")
	List<Thread> getSectionThreads(Long sectionId);
	
	@Query("select count(*) from Thread as thr where section.id = ?")
	Long getSectionThreadsCount(long sectionId);
}
