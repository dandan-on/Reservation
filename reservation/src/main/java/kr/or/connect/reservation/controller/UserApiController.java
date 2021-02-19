package kr.or.connect.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserApiController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/loginerror")
	public String loginError() {
		return "loginerror";
	}	
	
	@RequestMapping("/loginsuccess")
	public String loginSuccess() {
		return "loginsuccess";
	}	
	
}
