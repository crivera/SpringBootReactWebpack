package com.config;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.model.User;

/**
 * 
 * @author crivera
 *
 */
@ControllerAdvice
public class WebDataBinder {

	/**
	 * 
	 * @param authentication
	 * @return
	 */
	@ModelAttribute("currentUser")
	public User getCurrentUser(Authentication authentication) {
		return (authentication == null) ? null : (User) authentication.getPrincipal();
	}

}
