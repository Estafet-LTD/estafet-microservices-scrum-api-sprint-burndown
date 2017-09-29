package com.estafet.microservices.api.sprint.burndown.jms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.services.SprintService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(value = "newSprintConsumer")
public class NewSprintConsumer {

	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;

	@Autowired
	private SprintService sprintService;

	public void onMessage(String message) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Sprint sprint = mapper.readValue(message, Sprint.class);
			if (sprintBurndownDAO.getSprintBurndown(sprint.getId()) == null) {
				sprintBurndownDAO.create(sprint.addDays(sprintService.getSprintDays(sprint.getId())));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
