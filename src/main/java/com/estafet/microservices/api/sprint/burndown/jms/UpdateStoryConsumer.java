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
public class UpdateStoryConsumer {

	public final static String TOPIC = "update.story.topic";
	
	@Autowired
	private Tracer tracer;
	
	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private MessageEventHandler messageEventHandler;

	@JmsListener(destination = TOPIC, containerFactory = "myFactory")
	public void onMessage(String message, @Header("message.event.interaction.reference") String reference) {
		try {
			if (messageEventHandler.isValid(TOPIC, reference)) {
				sprintService.updateStory(Story.fromJSON(message));
			}
		} finally {
			if (tracer.activeSpan() != null) {
				tracer.activeSpan().close();	
			}
		}
	}

}
