package com.unisys.ApiJobManager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unisys.ApiJobManager.model.Job;
import com.unisys.ApiJobManager.model.Task;
import com.unisys.ApiJobManager.repository.TaskRepository;

@Service("taskService")
public class TaskServicelmpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public void createTasks(Job job) {

		List<Task> tasks = job.getTasks();

		for (int i = 0; i < tasks.size(); i++) {
			int idTask = tasks.get(i).getId();
			if (taskRepository.findById(idTask).isPresent() == false) {
				String name = tasks.get(i).getName();
				int weight = tasks.get(i).getWeight();
				boolean completed = tasks.get(i).isCompleted();
				Date createdAt = tasks.get(i).getCreatedAt();
				Task task = new Task(idTask, name, weight, completed, createdAt, job);
				taskRepository.save(task);
				
			}
		}
	}

}