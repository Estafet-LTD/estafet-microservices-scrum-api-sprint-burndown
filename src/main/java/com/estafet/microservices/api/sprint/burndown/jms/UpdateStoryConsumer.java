package com.estafet.microservices.api.sprint.burndown.jms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.model.Story;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(value = "updateStoryConsumer")
public class UpdateStoryConsumer {

	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;

	@Transactional
	public void onMessage(String message) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Story story = mapper.readValue(message, Story.class);
			if (story.getStatus().equals("In Progress")) {
				Sprint sprint = sprintBurndownDAO.getSprintBurndown(story.getSprintId());
				sprint.update(story);
				sprintBurndownDAO.update(sprint);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
