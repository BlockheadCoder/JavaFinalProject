package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.User;
import ca.sheridancollege.repository.UserRepo;

@Controller
public class MainTroll {
	
	@Autowired
	private UserRepo userRepo;
	
	private List<User> allUsers = new ArrayList<User>();
	private List<User> currentUsers = new ArrayList<User>();
	
	@GetMapping("/")
	public String homeTroll() {
		
		currentUsers.addAll(Arrays.asList(new User[] {
				User.builder().name("Thomas").type(User.UserType.ARTIST).build(),
				User.builder().name("Roman").type(User.UserType.ARTIST).build()
			}));
		
		allUsers.addAll(Arrays.asList(new User[] {
				User.builder().name("Fern").type(User.UserType.ARTIST).build(),
				User.builder().name("Andy").type(User.UserType.CUSTOMER).build()
		}));
		
		allUsers.addAll(currentUsers);
		
		return "login.html";
	}
	
	@GetMapping("/login")
	public String login(
			@RequestParam("loggedInUser") String name,
			@RequestParam("recipient") String recipient,
			Model model) {
		
		for (User user : allUsers) {
			
		}
		
		model.addAttribute("myId",name);
		model.addAttribute("recId",recipient);
		
		return "index.html";
	}
	
	@GetMapping("/conversation")
	public String conversation(Model model) {
		
		model.addAttribute("currentConversations", currentUsers);
		
		return "conversation.html";
	}
	
	@GetMapping("/conversationAdd")
	public String conversationAdd(Model model) {
		
		model.addAttribute("currentConversations", allUsers);
		
		return "conversationAdd.html";
	}
	
	@PostMapping("/conversationAdd")
	public String conversationAddForm(Model model) {
		
		
		
		model.addAttribute("currentConversations", allUsers);
		
		return "conversationAdd.html";
	}
	

		
}
