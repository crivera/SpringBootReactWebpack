package com.controller;

import java.io.IOException;

import javax.script.ScriptException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author crivera
 *
 */
@Controller
public class AccountController {

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/profile")
	public String index(Model model) throws IOException, ScriptException {
		return "account/profile";
	}

}
