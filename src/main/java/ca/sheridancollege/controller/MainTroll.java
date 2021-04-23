package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@GetMapping("/")
	public String homeTroll() {
		List<User> test = userRepo.getUsersInConversationWithLoggedInUser(1);
		for (User u : test) {
			System.out.println(u);
		}
		
		return "login.html";
	}
	
	@GetMapping("/account")
	public String account(Authentication authentication, Model model) {
		
		model.addAttribute("loggedInUser", authentication.getName());
		
		User loggedInUser = userRepo.findByName(authentication.getName());
		model.addAttribute("currentUsers", 
				userRepo.getUsersInConversationWithLoggedInUser(loggedInUser.getId()));
		
		/* ALWAYS ACTIVE RIGHT NOW */
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			if ("ROLE_ARTIST".equals(ga.getAuthority())) {
				model.addAttribute("artist", "artist");
			}
		}

		return "account.html";
	}
	
	@GetMapping("/possibleConversations")
	public String conversationAdd(Model model) {
		model.addAttribute("allUsers", userRepo.findAll()); 
		//TODO: restrict current user from list
		
		//TODO: need a check to ensure user isn't already in user's convo list
		//      use getUsersInConversationWithLoggedInUser
		return "newConversation.html";
	}
	
	@GetMapping("/conversationAdd/{userId}")
	public String conversationAddForm(@PathVariable int userId, Model model, Authentication authentication) {
		
		User newUser = userRepo.findById(userId).get();
		
		model.addAttribute("newUser", newUser);
		model.addAttribute("loggedInUser", authentication.getName());
		
		User loggedInUser = userRepo.findByName(authentication.getName());
		model.addAttribute("currentUsers", 
				userRepo.getUsersInConversationWithLoggedInUser(loggedInUser.getId()));
		
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			if ("ROLE_ARTIST".equals(ga.getAuthority())) {
				model.addAttribute("artist", "artist");
			}
		}

		return "account.html";
	}
	
	@PostMapping("/message")
	public String message2(Authentication authentication, @RequestParam String recipientName, Model model){
		
		model.addAttribute("myId",authentication.getName());
		model.addAttribute("recId",recipientName);
		
		return "message.html";
	}
	
	@GetMapping("/error")
	public String error() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "redirect:/";
	}
	
	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}	
}
