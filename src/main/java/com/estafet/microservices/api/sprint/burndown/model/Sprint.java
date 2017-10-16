package com.estafet.microservices.api.sprint.burndown.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "SPRINT")
public class Sprint {

	@Id
	@Column(name = "SPRINT_ID")
	private Integer id;

	@Column(name = "START_DATE", nullable = false)
	private String startDate;

	@Column(name = "SPRINT_NUMBER", nullable = false)
	private Integer number;

	@Column(name = "NO_DAYS", nullable = false)
	private Integer noDays;

	@OrderBy("dayNo ASC")
	@OneToMany(mappedBy = "sprintDaySprint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SprintDay> sprintDays = new ArrayList<SprintDay>();

	public Integer getId() {
		return id;
	}

	public String getStartDate() {
		return startDate;
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getNoDays() {
		return noDays;
	}

	public List<SprintDay> getSprintDays() {
		return sprintDays;
	}

	public Sprint addDays(List<String> days) {
		int dayNo = 1;
		for (String day : days) {
			sprintDays.add(new SprintDay().setDayNo(dayNo++).setSprintDay(day).setSprintDaySprint(this));
		}
		return this;
	}

	public Sprint update(List<Task> tasks) {
		for (Task task : tasks) {
			update(task);
		}
		return this;
	}
	
	public Sprint update(Task task) {
		if (task.getRemainingUpdated() == null) {
			sprintDays.get(0).update(task);
		} else {
			SprintDay sprintDay = getSprintDay(task.getRemainingUpdated());
			sprintDay.update(task);
			backfill(sprintDay.getDayNo(), task);
		}
		return this;
	}
	
	private void backfill(int dayNo, Task task) {
		for (int i = 0; sprintDays.get(i).getDayNo() < dayNo; i++) {
			sprintDays.get(i).backfill(task);
		}
	}
	
	public Sprint recalculate() {
		for (SprintDay sprintDay : sprintDays) {
			sprintDay.recalculate();
		}
		return this;
	}

	private SprintDay getSprintDay(String day) {
		for (SprintDay sprintDay : sprintDays) {
			if (day.equals(sprintDay.getSprintDay())) {
				return sprintDay;
			}
		}
		throw new RuntimeException("Invalid day - " + day);
	}
	
	public static Sprint fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, Sprint.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Sprint getAPI() {
		Sprint sprint = new Sprint();
		sprint.id = 1;
		sprint.noDays = 1;
		sprint.number = 1;
		sprint.sprintDays.add(SprintDay.getAPI());
		sprint.startDate = "2017-10-16 00:00:00";
		return sprint;
	}
	
}
