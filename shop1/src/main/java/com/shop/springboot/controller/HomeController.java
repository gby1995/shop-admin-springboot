package com.shop.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {
	
	@RequestMapping("/top")
	public String top(){
		return "admin/top";
	}
	
	@RequestMapping("/welcome")
	public String welcome(){
		return "admin/welcome";
	}
	
	@RequestMapping("/bottom")
	public String bottom(){
		return "admin/bottom";
	}
	@RequestMapping("/left")
	public String left(){
		return "admin/left";
	}
}
