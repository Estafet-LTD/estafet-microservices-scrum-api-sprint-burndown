package com.estafet.microservices.api.sprint.burndown.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.sprint.burndown.model.Sprint;
import com.estafet.microservices.api.sprint.burndown.services.SprintService;

import io.opentracing.ActiveSpan;
import io.opentracing.Tracer;

@Component
public class NewSprintConsumer {

	@Autowired
	private Tracer tracer;
	
	@Autowired
	private SprintService sprintService;

	@JmsListener(destination = "new.sprint.topic", containerFactory = "myFactory")
	public void onMessage(String message) {
		ActiveSpan span = tracer.activeSpan().log(message);
		try {
			sprintService.newSprint(Sprint.fromJSON(message));
		} finally {
			span.close();
		}
	}

}
