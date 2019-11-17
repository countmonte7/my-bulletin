package com.bulletin.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	private List<User> users = new ArrayList<User>();
	
	@GetMapping("/form")
	public String getSignUpPage() {
		return "form";
	}
	
	@GetMapping("/index")
	public String getHome() {
		return "index";
	}

	@PostMapping("/create")
	public String create(User user) {
		System.out.println("user : " + user);
		users.add(user);
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String getUserList(Model model) {
		model.addAttribute("users", users);
		return "list";
	}
}
