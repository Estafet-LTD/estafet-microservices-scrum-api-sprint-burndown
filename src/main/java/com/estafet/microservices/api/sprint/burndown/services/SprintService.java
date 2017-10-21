package com.estafet.microservices.api.sprint.burndown.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;

@Service
public class SprintService {

	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	public List<String> getSprintDays(int sprintId) {
		return restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/days",
				List.class, sprintId);
	}
	
	public String getSprintDay(int sprintId) {
		return restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/day",
				String.class, sprintId);
	}
	
	@Transactional(readOnly = true)
	public Sprint getSprintBurndown(int sprintId) {
		Sprint sprintBurndown = sprintBurndownDAO.getSprintBurndown(sprintId);
		return sprintBurndown.init();
	}
	
	@Transactional
	public void newSprint(Sprint sprint) {
		if (sprintBurndownDAO.getSprintBurndown(sprint.getId()) == null) {
			sprintBurndownDAO.create(sprint.addDays(getSprintDays(sprint.getId())));
		}
	}
	
}
