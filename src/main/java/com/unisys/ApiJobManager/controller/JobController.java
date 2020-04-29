package com.unisys.ApiJobManager.controller;


import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unisys.ApiJobManager.model.Job;
import com.unisys.ApiJobManager.model.ParentJob;
import com.unisys.ApiJobManager.repository.JobRepository;
import com.unisys.ApiJobManager.repository.ParentJobRepository;
import com.unisys.ApiJobManager.service.JobService;
import com.unisys.ApiJobManager.service.TaskService;


@RestController
@RequestMapping(value = "/jobs")
public class JobController {

	private JobRepository jobRepository;
	private ParentJobRepository parentJobRepository;
	private JobService jobService;
	private TaskService taskService;

	public JobController( JobRepository jobRepository,
			ParentJobRepository parentJobRepository, JobService jobService, TaskService taskService) {
		super();
		this.jobRepository = jobRepository;
		this.parentJobRepository = parentJobRepository;
		this.jobService = jobService;
		this.taskService = taskService;

	}

	@PostMapping
	public ResponseEntity<Job> Save(@RequestBody Job job) {
		// JOB A SER SALVO
		int id = job.getId();
		String name = job.getName();
		boolean active = job.isActive();
		Job jobSave = new Job(id, name, active);

		// Se o nome do job já existir na base
		if (jobService.nameEqual(jobSave)) {
			return new ResponseEntity<>(job, HttpStatus.CONFLICT);

		}

		// Se o JOB for o primeiro na base
		if (jobService.firstJob()) {
			jobSave.setParentJob(null);
			jobRepository.save(jobSave);
			// Tasks
			jobSave.setTasks(job.getTasks());
			taskService.createTasks(jobSave);
			return new ResponseEntity<>(jobSave, HttpStatus.OK);

		} else {
			jobSave.setParentJob(job.getParentJob());
		}

		// Só é possivel colocar como JOB parent, JOBS existentes
		if (job.getParentJob() != null && jobService.searchJobParent(job) == false) {
			return new ResponseEntity<>(job, HttpStatus.CONFLICT);

		} else {
			// Testar Arvore de Parentes
			if (jobSave.getParentJob() != null && jobService.validatorJobParent(jobSave)) {
				jobRepository.save(jobSave);

				// pegar dados de parent
				int idParent = jobSave.getParentJob().getId();
				String nameParent = jobSave.getParentJob().getName();
				boolean activeParent = jobSave.getParentJob().isActive();
				ParentJob parentJob = new ParentJob(idParent, nameParent, activeParent, jobSave);
				parentJobRepository.save(parentJob);

				// Tasks
				jobSave.setTasks(job.getTasks());
				taskService.createTasks(jobSave);
			}
		}

		return new ResponseEntity<>(jobSave, HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<List<Job>> get(@RequestParam(value = "sortByWeight", required = false) Boolean sortByWeight) {
		final List<Job> jobs;
		try {
			jobs = jobRepository.findAll();
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		if ((sortByWeight != null) && (sortByWeight.booleanValue())) {
			Collections.sort(jobs, (job1, job2) -> job1.getSumTask().compareTo(job2.getSumTask()));
			Collections.reverse(jobs);

		}
		return new ResponseEntity<>(jobs, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Job>> getById(@PathVariable Integer id) {
		Optional<Job> job;
		try {
			job = jobRepository.findById(id);
			return new ResponseEntity<Optional<Job>>(job, HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<Optional<Job>>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Optional<Job>> deleteById(@PathVariable Integer id) {
		try {
			jobRepository.deleteById(id);
			return new ResponseEntity<Optional<Job>>(HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<Optional<Job>>(HttpStatus.NOT_FOUND);
		}

	}
}
