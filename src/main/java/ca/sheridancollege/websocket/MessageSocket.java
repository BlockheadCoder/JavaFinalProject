package ca.sheridancollege.websocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import ca.sheridancollege.beans.Message;

@Controller
public class MessageSocket {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/usermessages/")
	public Message message(
			Message mesIn
			) throws Exception{
		
		System.out.println("Got it: "+mesIn.getMessage());
		System.out.println(mesIn.toString());
		System.out.println("Sending to "+"/socketOut/"+mesIn.getReceiverId());
		simpMessagingTemplate.convertAndSend("/socketOut/"+mesIn.getReceiverId(), mesIn);
		
		return new Message("hello");
	}
	
}
