package com.unisys.ApiJobManager.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Task {

	@Id
	@Column(nullable = false)
	@NotNull
	private int id;

	@Column(nullable = false)
	@NotNull
	private String name;

	@Column(nullable = false)
	@NotNull
	private int weight;

	@Column(nullable = false)
	@NotNull
	private boolean completed;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@ManyToOne
	@JoinColumn(name = "id_job", nullable = true)
	@JsonIgnore
	private Job job;

	public Task() {

	}

	public Task(int id, String name, int weight, boolean completed, String createdAt) {
		super();
		Date data;
		data = converter(createdAt);
		this.id=id;
		this.name = name;
		this.weight = weight;
		this.completed = completed;
		this.createdAt = data;
	}
	public Task(int id, String name, int weight, boolean completed, Date createdAt, Job job) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.completed = completed;
		this.createdAt = createdAt;
		this.job = job;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public int getId() {
		return id;
	}

}