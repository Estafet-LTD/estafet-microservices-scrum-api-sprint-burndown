package com.estafet.microservices.api.sprint.burndown.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "STORY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

	@Id
	@Column(name = "STORY_ID")
	private Integer id;

	@Column(name = "STATUS", nullable = false)
	private String status;

	@Transient
	private Integer sprintId;

	@JsonIgnore
	@OneToMany(mappedBy = "taskStory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Task> tasks = new HashSet<Task>();

	@ManyToOne
	@JoinColumn(name = "SPRINT_ID", nullable = true, referencedColumnName = "SPRINT_ID")
	private Sprint storySprint;

	public Task add(Task task) {
		Task updated = getTask(task.getId());
		if (updated == null) {
			task.setTaskStory(this);
			tasks.add(task);
			updated = task;
		}
		updated.setInitialHours(task.getInitialHours());
		updated.setRemainingHours(task.getRemainingHours());
		updated.setRemainingUpdated(task.getRemainingUpdated());
		return updated;
	}
	
	private Task getTask(Integer taskId) {
		for (Task task : tasks) {
			if (task.getId().equals(taskId)) {
				return task;
			}
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public Integer getSprintId() {
		return sprintId;
	}

	public Sprint getStorySprint() {
		return storySprint;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	void setStorySprint(Sprint storySprint) {
		this.storySprint = storySprint;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static Story fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, Story.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Story other = (Story) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
