package com.estafet.microservices.api.sprint.burndown.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.services.SprintService;

@Component
public class NewSprintConsumer {

	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;

	@Autowired
	private SprintService sprintService;

	@Transactional
	@JmsListener(destination = "new.sprint.topic", containerFactory = "myFactory")
	public void onMessage(Sprint sprint) {
		if (sprintBurndownDAO.getSprintBurndown(sprint.getId()) == null) {
			sprintBurndownDAO.create(sprint.addDays(sprintService.getSprintDays(sprint.getId())));
		}
	}

}
