package com.estafet.microservices.api.sprint.burndown.container.tests;

import com.estafet.microservices.scrum.lib.commons.jms.TopicProducer;

public class NewTaskTopicProducer extends TopicProducer {

	public NewTaskTopicProducer() {
		super("new.task.topic");
	}
	
	public static void send(String message) {
		new NewTaskTopicProducer().sendMessage(message);
	}

}
