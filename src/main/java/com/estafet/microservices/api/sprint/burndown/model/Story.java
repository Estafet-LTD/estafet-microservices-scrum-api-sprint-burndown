package com.estafet.microservices.api.sprint.burndown.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4332677156189712787L;

	private Integer id;

	private String status;

	private Integer sprintId;

	public Integer getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public Integer getSprintId() {
		return sprintId;
	}

}
