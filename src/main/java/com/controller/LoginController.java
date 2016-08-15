package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.FacebookService;

/**
 * 
 * @author crivera
 *
 */
@Controller
public class LoginController {

	@Autowired
	FacebookService facebookService;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String loginPage(Model model) {
		return "login/loginPage";
	}
	
}
