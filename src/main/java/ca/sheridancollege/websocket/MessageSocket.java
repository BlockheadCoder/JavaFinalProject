package ca.sheridancollege.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import ca.sheridancollege.beans.Message;
import ca.sheridancollege.repository.MessageRepo;

@Controller
public class MessageSocket {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private MessageRepo mRepo;
	
	@MessageMapping("/usermessages/")
	public Message message(Message mesIn) throws Exception{
		
		System.out.println("Got it: "+mesIn.getMessage());
		System.out.println(mesIn.toString());
		System.out.println("Sending to "+"/socketOut/"+mesIn.getReceiverId()+"/"+mesIn.getSenderId());
		simpMessagingTemplate.convertAndSend("/socketOut/"+mesIn.getReceiverId()+"/"+mesIn.getSenderId(), mesIn);
		
		//db insert goes here
		mRepo.save(mesIn);
		
		return new Message("hello");
	}
	
}
