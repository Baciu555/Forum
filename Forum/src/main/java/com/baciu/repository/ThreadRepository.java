package com.baciu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baciu.entity.Thread;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {

}
