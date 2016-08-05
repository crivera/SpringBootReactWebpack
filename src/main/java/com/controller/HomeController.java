package com.controller;

import java.io.IOException;
import java.util.List;

import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/home")
	public String index(Model model) throws IOException, ScriptException {
		List<String> list = jdbcTemplate.queryForList("select name from test", String.class);
		model.addAttribute("list", list);
		return "home/index";
	}

}
