package com.lab.software.engineering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

	@RequestMapping({"/report" ,"/employee"})
	public String index() {
		return "forward:/index.html";
	}
}
