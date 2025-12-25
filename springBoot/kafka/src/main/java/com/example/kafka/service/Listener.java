package com.example.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {
	
	@KafkaListener(topics = "myTopic", groupId = "myAnotherGroupId")
	public void listenToMyTopic(String messageReceived)
	{
		System.out.println("Message received is " + messageReceived);
	}
	
	@KafkaListener(topics = "myTopic", groupId = "myGroupId")
	public void anotherListenerToMyTopic(String messageReceived)
	{
		System.out.println("From another listener Message received is " + messageReceived);
	}
}
