package com.ktk.springbootsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	@GetMapping("/")
	public String hello() {
		return "Hello World";
	}
	
	@GetMapping("/good-by")
	public String goodBy() {
		return "Good bye!!";
	}
}
