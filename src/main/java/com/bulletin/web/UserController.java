package com.bulletin.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bulletin.domain.User;
import com.bulletin.domain.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if(user==null) {
			System.out.println("Login Fail-1");
			return "redirect:/users/loginForm";
		}
		if(!password.equals(user.getPassword())) {
			System.out.println("Login Fail-2");
			return "redirect:/users/loginForm";
		}
		System.out.println("Login Success");
		session.setAttribute("sessionedUser", user);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}
	

	@PostMapping("")
	public String create(User user) {
		System.out.println("user : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String getUserList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if(session.getAttribute("sessionedUser")==null) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = (User)session.getAttribute("sessionedUser");
		model.addAttribute("user", userRepository.findById(sessionedUser.getId()).get());
		return "/user/updateForm";
	}
	
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		User sessionedUser = (User)session.getAttribute("sessionedUser");
		User user = userRepository.findById(id).get();
		if(!updatedUser.getUserId().equals(sessionedUser.getUserId())) {
			return "redirect:/";
		}
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
