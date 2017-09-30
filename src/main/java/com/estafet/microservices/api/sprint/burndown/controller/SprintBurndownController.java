package com.estafet.microservices.api.sprint.burndown.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.services.SprintService;

@RestController
public class SprintBurndownController {

	@Autowired
	private SprintService sprintService;
	
	@GetMapping("/sprint/{id}/burndown")
	public Sprint getSprintBurndown(@PathVariable int id) {
		return sprintService.getSprintBurndown(id);
	}
	
}
