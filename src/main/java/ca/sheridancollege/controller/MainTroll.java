package ca.sheridancollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainTroll {
	
	
	@GetMapping("/")
	public String homeTroll() {
		
		return "login.html";
	}
	
	@GetMapping("/login")
	public String login(
			@RequestParam("loggedInUser") String name,
			@RequestParam("recipient") String recipient,
			Model model) {
		
		
		model.addAttribute("myId",name);
		model.addAttribute("recId",recipient);
		
		return "index.html";
	}
		
}
