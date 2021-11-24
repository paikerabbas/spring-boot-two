package com.saien.springboottwo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Autowired
	private JdbcUserDetailsManager userDetailsManager;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	@GetMapping("/hello")
	public String hello() {
		return "Hello Paiker!!";
	}

	@PostMapping("/user")
	public void addUser(@RequestBody UserRequest userRequest) {
		System.out.println("userRequestuserRequestuserRequestuserRequest");
		System.out.println(userRequest);
		userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		userDetailsManager.createUser(new SecureUser(userRequest));
	}

}
