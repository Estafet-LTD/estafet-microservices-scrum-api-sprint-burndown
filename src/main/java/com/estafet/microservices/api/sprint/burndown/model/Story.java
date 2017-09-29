package com.estafet.microservices.api.sprint.burndown.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

	private Integer id;

	private String status;

	private Integer sprintId;

	private List<Task> tasks;

	public Integer getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public Integer getSprintId() {
		return sprintId;
	}

	public List<Task> getTasks() {
		return tasks;
	}

}
