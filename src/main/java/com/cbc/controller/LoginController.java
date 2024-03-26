package com.cbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.cbc.model.MyUser;
import com.cbc.repository.UserRepository;

@Controller
public class LoginController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String homepPage() {
		return "home";
	}
	
	/*
	@GetMapping("/loginForm")
	public String login(User user) {
		return "login";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String loginProcess(@RequestParam("username") String username, @RequestParam("password") String password) {
		User dbUser=userRepository.findByUsername(username);
		
		if(dbUser!=null && (dbUser.getPassword()).equals(password)) {
			return "redirect:index";
		}
		
		return "Username or password wrong";
	}
	
	*/
	
	@GetMapping("/registration")
    public String registerForm(@ModelAttribute("myuser") MyUser myuser) {
        return "register";
    }

	@PostMapping("/register")
	public String register(@ModelAttribute("myuser") MyUser myuser) {
	    try {
	        // Encode the password before saving
	        String encodedPassword = passwordEncoder.encode(myuser.getPassword());
	        myuser.setPassword(encodedPassword);
	        
	        // Save the user
	        userRepository.save(myuser);
	        
	        // Redirect to the home page after successful registration
	        return "redirect:/";
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle registration failure
	        return "register"; // Return to the registration form with an error message, or redirect to an error page
	    }
	}
	
	@GetMapping("/index")
    public String index() {
        return "index"; 
    }

	
}
