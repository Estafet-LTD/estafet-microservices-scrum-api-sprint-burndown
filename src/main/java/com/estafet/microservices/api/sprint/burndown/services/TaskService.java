package com.estafet.microservices.api.sprint.burndown.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.sprint.burndown.dao.SprintBurndownDAO;
import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.model.Story;
import com.estafet.microservices.api.sprint.burndown.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TaskService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private StoryService storyService;
	
	@Autowired
	private SprintBurndownDAO sprintBurndownDAO;
	
	@SuppressWarnings({ "rawtypes" })
	public List<Task> getTasks(Story story) {
		List objects = restTemplate.getForObject(System.getenv("TASK_API_SERVICE_URI") + "/story/{id}/tasks", List.class,
				story.getId());
		List<Task> tasks = new ArrayList<Task>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			Task task = mapper.convertValue(object, new TypeReference<Task>() {
			});
			tasks.add(task);
		}
		return tasks;
	}
	
	@Transactional
	public void newTask(Task task) {
		Integer sprintId = storyService.getStory(task).getSprintId();
		if (sprintId != null) {
			Sprint sprint = sprintBurndownDAO.getSprintBurndown(sprintId).update(task);
			sprintBurndownDAO.update(sprint);
		}
	}
	
	@Transactional
	public void updateTask(Task task) {
		if (task.getRemainingUpdated() != null) {
			int sprintId = storyService.getStory(task).getSprintId();
			Sprint sprint = sprintBurndownDAO.getSprintBurndown(sprintId).update(task);
			sprintBurndownDAO.update(sprint);
		}
	}
	
}
