package com.example.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.service.KafkaProducer;

@RestController
@RequestMapping("/rest/api")
public class FetchMessageFromClient {

	@Autowired
	KafkaProducer kafkaProducer;
	
	@GetMapping(value = "/producerMsg")
	public String sendMessage(@RequestParam("message") String message)
	{
		kafkaProducer.sendMessageToTopic(message);
		return "Message sent!";
	}
}
