package com.controller.ws;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.model.User;

public class BaseWebsocket {

	/**
	 * as per websocket config you always gotta be logged in ... so this is fine
	 * @return
	 */
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (User) auth.getPrincipal();
	}
}
