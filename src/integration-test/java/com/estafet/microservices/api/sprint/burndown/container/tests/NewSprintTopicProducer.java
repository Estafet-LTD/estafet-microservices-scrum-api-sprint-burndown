package com.estafet.microservices.api.sprint.burndown.container.tests;

import com.estafet.microservices.scrum.lib.commons.jms.TopicProducer;

public class NewSprintTopicProducer extends TopicProducer {

	public NewSprintTopicProducer() {
		super("new.sprint.topic");
	}
	
	public static void send(String message) {
		new NewSprintTopicProducer().sendMessage(message);
	}

}
