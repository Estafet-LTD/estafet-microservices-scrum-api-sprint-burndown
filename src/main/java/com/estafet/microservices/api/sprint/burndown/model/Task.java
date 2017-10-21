package com.estafet.microservices.api.sprint.burndown.model;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "TASK")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

	@Id
	@Column(name = "TASK_ID")
	private Integer id;

	@Column(name = "INITIAL_HOURS", nullable = false)
	private Integer initialHours;

	@Column(name = "REMAINING_HOURS", nullable = false)
	private Integer remainingHours;

	@Transient
	private String remainingUpdated;

	@Transient
	private Integer storyId;
	
	@ManyToOne
	@JoinColumn(name = "SPRINT_ID", nullable = false, referencedColumnName = "SPRINT_ID")
	private Sprint taskSprint;

	public Integer getStoryId() {
		return storyId;
	}

	public Integer getId() {
		return id;
	}

	public Integer getInitialHours() {
		return initialHours;
	}

	public Integer getRemainingHours() {
		return remainingHours;
	}

	public String getRemainingUpdated() {
		return remainingUpdated;
	}

	void setTaskSprint(Sprint taskSprint) {
		this.taskSprint = taskSprint;
	}

	public static Task fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, Task.class);
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
		Task other = (Task) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
