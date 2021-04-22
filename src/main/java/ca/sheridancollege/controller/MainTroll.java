package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.User;
import ca.sheridancollege.repository.UserRepo;

import ca.sheridancollege.dao.DataAccessObject;

@Controller
public class MainTroll {
	
	@Autowired
	private UserRepo userRepo;
	
	private List<User> allUsers = new ArrayList<User>();
	private List<User> currentUsers = new ArrayList<User>();
	
	private String loggedInUser = "Roman";
	
	@GetMapping("/")
	public String homeTroll() {
		
		currentUsers.clear();
		allUsers.clear();
		
		currentUsers.addAll(Arrays.asList(new User[] {
				User.builder().id(0).name("Thomas").type(User.UserType.ARTIST).build(),
				User.builder().id(1).name("Roman").type(User.UserType.ARTIST).build()
			}));
		
		allUsers.addAll(Arrays.asList(new User[] {
				User.builder().id(2).name("Fern").type(User.UserType.ARTIST).build(),
				User.builder().id(3).name("Andy").type(User.UserType.CUSTOMER).build()
		}));
		
		allUsers.addAll(currentUsers);
		
		return "login.html";
	}
	
	@GetMapping("/error")
	public String error() {
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String login(
			@RequestParam("username") String name,
			@RequestParam("password") String password,
			Model model) {
		
		if(DataAccessObject.checkUsernamePassword(name, password)) {	
			System.out.println("Name: "+name);
			
			
			for (User user : allUsers) {
				if (user.getName().equals(name)) {
					
					return "redirect:/account";
				}
			}
		}
		
		model.addAttribute("loginFailure", false);
		
		return "redirect:/";
	}
	
	@GetMapping("/account")
	public String account(Model model) {
		
		model.addAttribute("loggedInUser", loggedInUser);
		model.addAttribute("currentUsers", currentUsers);
		
		return "account.html";
	}
	
	@GetMapping("/possibleConversations")
	public String conversationAdd(Model model) {
		
		model.addAttribute("allUsers", allUsers);
		
		return "newConversation.html";
	}
	
	@GetMapping("/conversationAdd/{userId}")
	public String conversationAddForm(@PathVariable int userId, Model model) {
		
		for (User user : allUsers) {
			if (user.getId() == userId) {
				currentUsers.add(user);
				break;
			}
		}
		
		return "redirect:/account";
	}

	/*
	@GetMapping("/message")
	public String message(@RequestParam("loggedInUser") String name,@RequestParam("recipient") String recipient, Model model) {
		
		model.addAttribute("myId",name);
		model.addAttribute("recId",recipient);
		
		return "message.html";
	}
	*/
	
	@GetMapping("/message/{loggedInUser}/{recipient}")
	public String message2(@PathVariable("loggedInUser") String name, @PathVariable("recipient") String recipient, Model model) {
		
		model.addAttribute("myId",name);
		model.addAttribute("recId",recipient);
		
		return "message.html";
	}


		
}
