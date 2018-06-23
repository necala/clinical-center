package drools.spring.example.controller;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class WebSocketController {
	
	private static Logger log = LoggerFactory.getLogger(WebSocketController.class);

	@Autowired
	private SimpMessagingTemplate template;
	
	
	
	@MessageMapping("/send/issue")
	public void onReceivedMessage(String message){
		this.template.convertAndSend("/issue", new SimpleDateFormat("HH:mm").format(new Date()) + " - " + message);
	}
	
	public void sendMessage(String message){
		this.template.convertAndSend("/issue", new SimpleDateFormat("HH:mm").format(new Date()) + " - " + message);
	}
	
}
