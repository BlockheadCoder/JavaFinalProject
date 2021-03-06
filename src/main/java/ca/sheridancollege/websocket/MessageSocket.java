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
		
		simpMessagingTemplate.convertAndSend("/socketOut/"+mesIn.getReceiverId()+"/"+mesIn.getSenderId(), mesIn);
		
		mRepo.save(mesIn);
		
		return new Message("Message Created!");
	}
	
}
