package com.estafet.microservices.api.sprint.burndown.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SPRINT_DAY")
public class SprintDay {

	@Id
	@SequenceGenerator(name = "SPRINT_DAY_ID_SEQ", sequenceName = "SPRINT_DAY_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPRINT_DAY_ID_SEQ")
	@Column(name = "SPRINT_DAY_ID")
	private Integer id;

	@Column(name = "DAY_NO", nullable = false)
	private Integer dayNo;

	@Column(name = "HOURS_TOTAL", nullable = false)
	private Integer hoursTotal = 0;

	@Column(name = "SPRINT_DAY", nullable = false)
	private String sprintDay;

	@ManyToOne
	@JoinColumn(name = "SPRINT_ID", nullable = false, referencedColumnName = "SPRINT_ID")
	private Sprint sprintDaySprint;
	
	@OneToMany(mappedBy = "taskUpdateSprintDay", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<TaskUpdate> updates = new ArrayList<TaskUpdate>();

	public Integer getId() {
		return id;
	}

	public Integer getDayNo() {
		return dayNo;
	}

	public String getSprintDay() {
		return sprintDay;
	}

	public Sprint getSprintDaySprint() {
		return sprintDaySprint;
	}

	public Integer getHoursTotal() {
		return hoursTotal;
	}

	public SprintDay setHoursTotal(Integer hoursTotal) {
		this.hoursTotal = hoursTotal;
		return this;
	}

	public SprintDay setDayNo(Integer dayNo) {
		this.dayNo = dayNo;
		return this;
	}

	public SprintDay setSprintDay(String sprintDay) {
		this.sprintDay = sprintDay;
		return this;
	}

	public SprintDay setSprintDaySprint(Sprint sprintDaySprint) {
		this.sprintDaySprint = sprintDaySprint;
		return this;
	}
	
	private TaskUpdate geTaskUpdate(Integer taskId) {
		for (TaskUpdate update : updates) {
			if (update.getTaskId().equals(taskId)) {
				return update;
			}
		}
		return null;
	}
	
	public void update(Task task) {
		TaskUpdate update = geTaskUpdate(task.getId());
		if (update == null) {
			update = new TaskUpdate().setTaskId(task.getId()).setTaskUpdateSprintDay(this);
			updates.add(update);
		}
		update.setRemainingHours(task.getRemainingHours());
	}
	
	public void recalculate() {
		this.hoursTotal = 0;
		for (TaskUpdate update : updates) {
			hoursTotal += update.getRemainingHours();
		}
	}

}
