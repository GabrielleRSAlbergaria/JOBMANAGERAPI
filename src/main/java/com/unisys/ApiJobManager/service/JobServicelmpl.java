package com.unisys.ApiJobManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unisys.ApiJobManager.model.Job;
import com.unisys.ApiJobManager.model.ParentJob;
import com.unisys.ApiJobManager.repository.JobRepository;

@Service("jobService")
public class JobServicelmpl implements JobService {

	private JobRepository jobRepository;

	public JobServicelmpl(JobRepository jobRepository) {
		super();
		this.jobRepository = jobRepository;
	}

	@Override
	public boolean nameEqual(Job job) {
		List<Job> jobs;
		jobs = jobRepository.findAll();
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).getName().equals(job.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean firstJob() {
		if (jobRepository.count() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean searchJobParent(Job job) {
		ParentJob parentJob = job.getParentJob();
		int id = parentJob.getId();
		Optional<Job> jobSearchId = jobRepository.findById(id);

		if (jobSearchId.isPresent()) {
			if (jobSearchId.get().getName().equals(parentJob.getName())
					&& jobSearchId.get().isActive() == parentJob.isActive())
				;
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean validatorJobParent(Job job) {
		ParentJob parentJob = job.getParentJob();

		while (parentJob != null) {
			if (parentJob.getName().equals(job.getName())) {
				return false;
			}
			int idSearch = parentJob.getId();
			Optional<Job> jobSearch = jobRepository.findById(idSearch);
			if (jobSearch.isPresent()) {
				parentJob = jobSearch.get().getParentJob();
			}

		}
		return true;
	}

}
