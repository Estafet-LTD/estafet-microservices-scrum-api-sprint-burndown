package com.estafet.microservices.api.sprint.burndown.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.sprint.burndown.event.MessageEventHandler;
import com.estafet.microservices.api.sprint.burndown.model.Story;
import com.estafet.microservices.api.sprint.burndown.services.SprintService;

import io.opentracing.Tracer;

@Component
public class NewStoryConsumer {

	@Autowired
	private Tracer tracer;
	
	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private MessageEventHandler messageEventHandler;

	@JmsListener(destination = "new.story.topic", containerFactory = "myFactory")
	public void onMessage(String message, @Header("message.event.interaction.reference") String reference) {
		try {
			if (messageEventHandler.isValid("new.story.topic", reference)) {
				sprintService.newStory(Story.fromJSON(message));
			}
		} finally {
			if (tracer.activeSpan() != null) {
				tracer.activeSpan().close();	
			}
		}
	}

}
