package com.unisys.ApiJobManager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;


@Entity
public class ParentJob {
	@Id
	@Column(nullable = false)
	@NotNull
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "active")
	private boolean active;
	
	@OneToOne
	@JoinColumn(name = "id_job")
	private Job job;
	
	public ParentJob() {
		
	}

	public ParentJob(int id, String name, boolean active, Job job) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
		this.job = job;
	}


	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

}