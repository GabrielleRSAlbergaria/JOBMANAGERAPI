package com.unisys.ApiJobManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.unisys.ApiJobManager.model.Job;
import com.unisys.ApiJobManager.service.JobService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	@Autowired 
	private JobService jobService;
	
	@Test
	public void checkJobEqualityByNameTestOk() {
		Job job2 = new Job(100, "Job1", true);
	    Assert.assertTrue(jobService.nameEqual(job2) == true);

	}


	

}
