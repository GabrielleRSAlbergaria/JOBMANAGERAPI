package com.unisys.ApiJobManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unisys.ApiJobManager.model.ParentJob;

@Repository
@Transactional
public interface ParentJobRepository extends JpaRepository<ParentJob, Integer> {
	

}