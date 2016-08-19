package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.User;
import com.service.UserService;

/**
 * 
 * @author crivera
 *
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	UserService userService;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET)
	public String profile(Model model) {
		return "profile/profile";
	}

	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/currentUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> currentUser(@ModelAttribute("currentUser") User currentUser) {
		// TODO: maybe get a latest version of the user
		return new ResponseEntity<Object>(currentUser, HttpStatus.OK);
	}

	/**
	 * 
	 * @param userName
	 * @param email
	 * @param currentUser
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> update(@RequestBody User updatedUser,
			@ModelAttribute("currentUser") User currentUser) {

		currentUser.setUserName(updatedUser.getUserName());
		currentUser.setEmail(updatedUser.getEmail());
		currentUser = userService.updateUser(currentUser);
		return new ResponseEntity<Object>(currentUser, HttpStatus.OK);
	}

}
