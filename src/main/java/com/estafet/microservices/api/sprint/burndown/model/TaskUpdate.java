package com.estafet.microservices.api.sprint.burndown.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TASK_UPDATE")
public class TaskUpdate {

	@Id
	@SequenceGenerator(name = "TASK_UPDATE_ID_SEQ", sequenceName = "TASK_UPDATE_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASK_UPDATE_ID_SEQ")
	@Column(name = "TASK_UPDATE_ID")
	private Integer id;

	@Column(name = "TASK_ID", nullable = false)
	private Integer taskId;

	@Column(name = "REMAINING_HOURS", nullable = false)
	private Integer remainingHours;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "SPRINT_DAY_ID", nullable = false, referencedColumnName = "SPRINT_DAY_ID", foreignKey = @ForeignKey(name = "TASK_UPDATE_TO_SPRINT_DAY_FK"))
	private SprintDay taskUpdateSprintDay;

	public Integer getId() {
		return id;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public TaskUpdate setTaskId(Integer taskId) {
		this.taskId = taskId;
		return this;
	}

	public Integer getRemainingHours() {
		return remainingHours;
	}

	public TaskUpdate setRemainingHours(Integer remainingHours) {
		this.remainingHours = remainingHours;
		return this;
	}

	public SprintDay getTaskUpdateSprintDay() {
		return taskUpdateSprintDay;
	}

	public TaskUpdate setTaskUpdateSprintDay(SprintDay taskUpdateSprintDay) {
		this.taskUpdateSprintDay = taskUpdateSprintDay;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
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
		TaskUpdate other = (TaskUpdate) obj;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

}
