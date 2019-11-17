package com.bulletin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	@GetMapping("/helloworld")
	public String welcome(Model model) {
		model.addAttribute("name", "kwangQ");
		model.addAttribute("company", "<b>nice</b>");
		model.addAttribute("age", true);
		return "welcome";
	}
	

}
