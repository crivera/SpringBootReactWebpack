package com.controller;

import java.io.IOException;

import javax.script.ScriptException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author crivera
 *
 */
@Controller
public class HomeController {

	@RequestMapping("/home")
	public String index(Model model) throws IOException, ScriptException {
		return "home/landingPage";
	}
	
	@RequestMapping("/aroundMe")
	public String AroundMe(Model model) throws IOException, ScriptException {
		return "home/aroundMe";
	}
}
