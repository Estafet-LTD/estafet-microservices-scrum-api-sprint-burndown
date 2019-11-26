package com.estafet.microservices.api.sprint.burndown.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.services.SprintService;

import io.opentracing.Tracer;

@RestController
public class SprintBurndownController {

	@Value("${app.version}")
	private String appVersion;
	
	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private Tracer tracer;	
	
	@GetMapping("/api")
	public Sprint getAPI() {
		tracer.activeSpan().deactivate();
		return Sprint.getAPI(appVersion);
	}
	
	@GetMapping("/sprint/{id}/burndown")
	public Sprint getSprintBurndown(@PathVariable int id) {
		return sprintService.getSprintBurndown(id);
	}
	
}
