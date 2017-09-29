package com.estafet.microservices.api.sprint.burndown.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

	private Integer id;

	private Integer remainingHours;

	private String remainingUpdated;

	public Integer getId() {
		return id;
	}

	public Integer getRemainingHours() {
		return remainingHours;
	}

	public String getRemainingUpdated() {
		return remainingUpdated;
	}

}
