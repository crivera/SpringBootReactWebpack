package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author crivera
 *
 */
@Controller
public class HomeController {

	@RequestMapping("/home")
	public String index() {
		return "home/index";
	}

}
