package ca.sheridancollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.dao.DataAccessObject;

@Controller
public class MainTroll {
	
	
	@GetMapping("/")
	public String homeTroll() {
		
		return "login.html";
	}
	
	@PostMapping("/login")
	public String login(
			@RequestParam("username") String name,
			@RequestParam("password") String password,
			Model model) {
		
		if(DataAccessObject.checkUsernamePassword(name, password)) {	
			System.out.println("Name: "+name);
			model.addAttribute("loggedinUser", name);
			return "account.html";
		}
		
		model.addAttribute("loginFailure", false);
		
		return "redirect:/login";
		
		
	}
	
	@GetMapping("/message")
	public String message(
			@RequestParam("loggedInUser") String name,
			@RequestParam("recipient") String recipient,
			Model model) {
		
		
		model.addAttribute("myId",name);
		model.addAttribute("recId",recipient);
		
		return "message.html";
	}
	
	@GetMapping("/error")
	public String error() {
		return "redirect:/login";
	}
		
}
