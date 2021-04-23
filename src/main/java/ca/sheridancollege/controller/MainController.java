package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Message;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.repository.MessageRepo;
import ca.sheridancollege.repository.UserRepo;


@Controller
public class MainController {
	
	@Autowired
	@Lazy
	private UserRepo userRepo;
	
	@Autowired
	private MessageRepo mRepo;
	
	@GetMapping("/")
	public String homeController() {
		return "login.html";
	}
	
	@GetMapping("/account")
	public String goConversationPage(Authentication authentication, Model model) {
		
		model.addAttribute("loggedInUser", userRepo.findByEmail(authentication.getName()).getName());
		
		User loggedInUser = userRepo.findByEmail(authentication.getName());
		model.addAttribute("currentUsers", 
				userRepo.getUsersInConversationWithLoggedInUser(loggedInUser.getId()));
		
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			if ("ROLE_ARTIST".equals(ga.getAuthority())) {
				model.addAttribute("artist", "artist");
			}
		}

		return "conversations.html";
	}
	
	@GetMapping("/possibleConversations")
	public String goConversationAddPage(Authentication authentication, Model model) {

		User loggedInUser = userRepo.findByEmail(authentication.getName());
		
		List<User> allUsers = new ArrayList<User>();
		Iterator<User> iteratorAllUsers = userRepo.findAll().iterator();
		while(iteratorAllUsers.hasNext()) {
			allUsers.add(iteratorAllUsers.next());
		}
		
		Iterator<User> iteratorConversationUsers = userRepo.getUsersInConversationWithLoggedInUser(loggedInUser.getId()).iterator();
		while(iteratorConversationUsers.hasNext()) {
			User user = iteratorConversationUsers.next();
			if (allUsers.contains(user)) {
				allUsers.remove(user);
			}
		}
		allUsers.remove(loggedInUser);
		
		model.addAttribute("allUsers", allUsers); 
		
		return "newConversation.html";
	}
	
	@PostMapping("/conversationAdd")
	public String conversationAdd(@RequestParam String name, Model model, Authentication authentication) {
		
		User newUser = userRepo.findByEmail(name);
		
		model.addAttribute("newUser", newUser);
		model.addAttribute("loggedInUser", userRepo.findByEmail(authentication.getName()).getName());
		
		User loggedInUser = userRepo.findByEmail(authentication.getName());
		model.addAttribute("currentUsers", 
				userRepo.getUsersInConversationWithLoggedInUser(loggedInUser.getId()));
		
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			if ("ROLE_ARTIST".equals(ga.getAuthority())) {
				model.addAttribute("artist", "artist");
			}
		}

		return "conversations.html";
	}
	
	@PostMapping("/message")
	public String goMessagingPage(Authentication authentication, @RequestParam String recipientName, Model model){
		
		User myself = userRepo.findByEmail(authentication.getName());
		User yourself = userRepo.findByEmail(recipientName);
		
		model.addAttribute("myId", myself.getId()); 
		model.addAttribute("recId", yourself.getId());
		
		model.addAttribute("myName", myself.getName());
		model.addAttribute("yourName", yourself.getName());
		
		List<Message> allMessages = mRepo.getAllMessagesForSenderAndReciever(myself.getId(), yourself.getId());
		model.addAttribute("allMessages", allMessages);
		
		return "message.html";
	}
	
	@GetMapping("/error")
	public String error() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	
	@GetMapping("/accessdenied")
	public String accessDenied() {
		return "accessdenied.html";
	}
}
