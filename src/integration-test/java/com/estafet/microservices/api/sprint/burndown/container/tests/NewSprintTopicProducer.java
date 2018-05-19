package com.estafet.microservices.api.sprint.burndown.container.tests;

public class NewSprintTopicProducer extends TopicProducer {

	public NewSprintTopicProducer() {
		super("new.sprint.topic");
	}
	
	public static void send(String message) {
		new NewSprintTopicProducer().sendMessage(message);
	}

}
