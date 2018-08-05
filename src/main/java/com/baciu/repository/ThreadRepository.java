package com.baciu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.baciu.entity.Thread;

@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, Long>, CrudRepository<Thread, Long> {
	
	@Query("from Thread as thr where section.id = ? order by thr.entryDate desc")
	List<Thread> getSectionThreads(Long sectionId);
	
	@Query("select count(*) from Thread as thr where section.id = ?")
	Long getSectionThreadsCount(long sectionId);
	
	List<Thread> findByContentContaining(String searchedText);
	List<Thread> findBySubjectContaining(String searchedText);
	
	@Query("select t from Thread t where t.section.id = ?")
	Page<Thread> getThreads(Long sectionId, Pageable pageable);
}
