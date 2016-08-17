package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.AppError;
import com.model.Chat;
import com.service.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	ChatService chatService;

	/**
	 * 
	 * @param chat
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> createNewChat(@RequestBody Chat chat) {
		AppError error = chat.validate();
		if (error != null) {
			return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		}
		chat = chatService.create(chat);
		return new ResponseEntity<Object>(chat, HttpStatus.OK);
	}

	/**
	 * 
	 * @param topLeft
	 * @param bottomRight
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getChatsInBounds(
			@RequestParam(value = "topRightLat", required = true) double topRightLat,
			@RequestParam(value = "topRightLng", required = true) double topRightLng,
			@RequestParam(value = "bottomLeftLat", required = true) double bottomLeftLat,
			@RequestParam(value = "bottomLeftLng", required = true) double bottomLeftLng) {

		List<Chat> chats = chatService.getChatsInBounds(topRightLat, topRightLng, bottomLeftLat, bottomLeftLng);
		return new ResponseEntity<Object>(chats, HttpStatus.OK);
	}
}
