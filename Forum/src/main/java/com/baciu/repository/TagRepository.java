package com.baciu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baciu.entity.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

}
