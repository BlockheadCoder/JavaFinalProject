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
	
	private List<User> allUsers = new ArrayList<User>();
	private List<User> currentUsers = new ArrayList<User>();
	
	@GetMapping("/")
	public String homeTroll() {
		
		System.out.println("here");
		
		currentUsers.clear();
		allUsers.clear();
		
		userRepo.deleteAll();
		
		userRepo.saveAll(Arrays.asList(new User[] {
				User.builder().id(1).name("Artist").password(encodePassword("123")).type(User.UserType.ROLE_ARTIST).build(),
				User.builder().id(2).name("Customer").password(encodePassword("123")).type(User.UserType.ROLE_CUSTOMER).build()
		}));
		
		currentUsers.addAll(Arrays.asList(new User[] {
				User.builder().id(3).name("Thomas").password(encodePassword("123")).type(User.UserType.ROLE_ARTIST).build(),
				User.builder().id(4).name("Roman").password(encodePassword("123")).type(User.UserType.ROLE_ARTIST).build()
		}));
		
		allUsers.addAll(Arrays.asList(new User[] {
				User.builder().id(5).name("Fern").password(encodePassword("123")).type(User.UserType.ROLE_ARTIST).build(),
				User.builder().id(6).name("Andy").password(encodePassword("123")).type(User.UserType.ROLE_ARTIST).build()
		}));
		
		allUsers.addAll(currentUsers);
		
		userRepo.saveAll(allUsers);
		
		return "login.html";
	}
	
	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	@GetMapping("/error")
	public String error() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "redirect:/";
	}
	
	@GetMapping("/account")
	public String account(Authentication authentication, Model model) {
		
		model.addAttribute("loggedInUser", authentication.getName());
		model.addAttribute("currentUsers", currentUsers);
		
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			if ("ROLE_ARTIST".equals(ga.getAuthority())) {
				model.addAttribute("artist", "artist");
			}
		}

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
				if (!currentUsers.contains(user)) {
					currentUsers.add(user);
				}

				break;
			}
		}
		
		return "redirect:/account";
	}
	
	@PostMapping("/message")
	public String message2(Authentication authentication, @RequestParam String recipientName, Model model){
		
		model.addAttribute("myId",authentication.getName());
		model.addAttribute("recId",recipientName);
		
		return "message.html";
	}
		
}
