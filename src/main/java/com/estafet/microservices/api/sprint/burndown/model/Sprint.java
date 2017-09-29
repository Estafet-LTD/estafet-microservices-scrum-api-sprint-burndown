package com.estafet.microservices.api.sprint.burndown.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	public Sprint update(Story story) {
		for (Task task : story.getTasks()) {
			update(task);
		}
		return this;
	}
	
	public Sprint update(Task task) {
		if (task.getRemainingUpdated() == null) {
			sprintDays.get(0).update(task);
		} else {
			getSprintDay(task.getRemainingUpdated()).update(task);
		}
		return this;
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

}
