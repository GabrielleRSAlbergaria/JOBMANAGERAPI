package com.unisys.ApiJobManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unisys.ApiJobManager.model.Job;

@Repository("jobRepository")
public interface JobRepository extends JpaRepository<Job, Integer> {


}
