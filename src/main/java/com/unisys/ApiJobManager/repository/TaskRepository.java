package com.unisys.ApiJobManager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unisys.ApiJobManager.model.Task;


@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Integer> {
	@Query(value = "SELECT t FROM Task t WHERE t.createdAt >= :createdAt")
	List<Task> getTaskFromCreatedAt(Date createdAt);
	
}
