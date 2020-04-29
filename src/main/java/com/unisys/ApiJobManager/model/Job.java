package com.unisys.ApiJobManager.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Job {
	
	@Id
	@Column(nullable = false)
	@NotNull
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "active")
	private boolean active;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "job")
	private ParentJob parentJob;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
	private List<Task> tasks;

	public Job() {
		
	}
	public Job(int id,String name, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
		
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

	public ParentJob getParentJob() {
		return parentJob;
	}

	public void setParentJob(ParentJob parentJob) {
		this.parentJob = parentJob;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	@Transient
	@JsonIgnore
	public Double getSumTask() {
		Double soma = (double) 0;
		for(int i =0; i< tasks.size(); i++) {
			soma+= tasks.get(i).getWeight();
		}
		return soma;
	}
	
}