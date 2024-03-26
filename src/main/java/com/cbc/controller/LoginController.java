package com.cbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@GetMapping("/login")
	public String login(@RequestParam(name = "error", required = false) String error, Model model) {
	    if (error != null) {
	        // If error parameter is present, add an error message to the model
	        model.addAttribute("error", "Invalid username or password");
	        // Return the login page with error message
	        return "login";
	    }
	    // If no error, just return the login page
	    return "login";
	}
	
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
