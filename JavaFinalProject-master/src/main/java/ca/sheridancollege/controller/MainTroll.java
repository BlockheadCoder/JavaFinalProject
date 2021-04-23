package ca.sheridancollege.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.repository.MessageRepo;
import ca.sheridancollege.repository.RoleRepo;
import ca.sheridancollege.repository.UserRepo;

import ca.sheridancollege.dao.DataAccessObject;

@Controller
public class MainTroll {
	
	@Autowired
	@Lazy
	private UserRepo userRepo;

	@Autowired
	private MessageRepo messageRepo;
	
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	@GetMapping("/")
	public String homeTroll() {
		return "login.html";
	}
	
	@GetMapping("/account")
	public String account(Authentication authentication, Model model) {
		
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
	
	@GetMapping("/possibleConversations")
	public String conversationAdd(Authentication authentication, Model model) {

		User loggedInUser = userRepo.findByName(authentication.getName());
		
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
	public String conversationAddForm(@RequestParam String name, Model model, Authentication authentication) {
		
		User newUser = userRepo.findByName(name);
		
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
	
	@GetMapping("/accessdenied")
	public String accessDenied() {
		return "accessdenied.html";
	}
	
	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}	
}
