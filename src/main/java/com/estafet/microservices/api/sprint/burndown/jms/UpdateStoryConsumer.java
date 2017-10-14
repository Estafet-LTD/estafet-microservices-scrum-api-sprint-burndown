package com.estafet.microservices.api.sprint.burndown.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.sprint.burndown.model.Story;
import com.estafet.microservices.api.sprint.burndown.services.StoryService;

import io.opentracing.ActiveSpan;
import io.opentracing.Tracer;

@Component
public class UpdateStoryConsumer {

	@Autowired
	private Tracer tracer;
	
	@Autowired
	private StoryService storyService;

	@JmsListener(destination = "update.story.topic", containerFactory = "myFactory")
	public void onMessage(String message) {
		ActiveSpan span = tracer.activeSpan().log(message);
		try {
			storyService.updateStory(Story.fromJSON(message));
		} finally {
			span.close();
		}
	}

}
