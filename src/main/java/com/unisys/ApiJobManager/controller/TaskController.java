package com.unisys.ApiJobManager.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unisys.ApiJobManager.model.Task;
import com.unisys.ApiJobManager.repository.TaskRepository;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@PostMapping
	public ResponseEntity<Task> save(@RequestBody Task task) {
		if(taskRepository.findById(task.getId()).isPresent()) {
		return new ResponseEntity<>(task, HttpStatus.CONFLICT);
		}
		taskRepository.save(task);
		return new ResponseEntity<>(task, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Task>> getAll(@RequestParam String $createdAt) {
		Date sql = converter($createdAt);
		List<Task> tasks = taskRepository.getTaskFromCreatedAt(sql);
		return new ResponseEntity<>(tasks, HttpStatus.OK);

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Task>> getById(@PathVariable Integer id) {
		try {
			Optional<Task> task = taskRepository.findById(id);
			return new ResponseEntity<>(task, HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<Optional<Task>>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Optional<Task>> deleteById(@PathVariable Integer id) {
		try {
			taskRepository.deleteById(id);
			return new ResponseEntity<Optional<Task>>(HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<Optional<Task>>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Task> update(@PathVariable Integer id, @RequestBody Task newTask) {
		
		return taskRepository.findById(id).map(task ->{
			task.setName(newTask.getName());
			task.setWeight(newTask.getWeight());
			task.setCompleted(newTask.isCompleted());
			task.setCreatedAt(newTask.getCreatedAt());
			task.setJob(task.getJob());
			Task taskUpdate = taskRepository.save(task);
			return ResponseEntity.ok().body(taskUpdate);
		}).orElse(ResponseEntity.notFound().build());
	}

	public Date converter(String data) {
		java.sql.Date sql = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsed = format.parse(data);
			sql = new java.sql.Date(parsed.getTime());

		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return sql;
	}
}
