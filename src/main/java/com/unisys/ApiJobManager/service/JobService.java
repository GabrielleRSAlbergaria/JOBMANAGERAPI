package com.unisys.ApiJobManager.service;

import com.unisys.ApiJobManager.model.Job;

public interface JobService {
	 boolean nameEqual(Job job);
	 boolean firstJob();
	 boolean searchJobParent(Job job);
	 boolean validatorJobParent(Job job);
	}