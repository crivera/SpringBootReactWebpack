package com.controller.ws;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.model.Message;
import com.model.User;

/**
 * 
 * @author Christopher
 *
 */
@Controller
public class ChatWebsocket extends BaseWebsocket {

	/**
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	@MessageMapping("/chat/{id}/join")
	@SendTo("/chat/{id}/join")
	public User joinChat(@DestinationVariable("id") long id) {
		return getCurrentUser();
	}

	/**
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	@MessageMapping("/chat/{id}/leave")
	@SendTo("/chat/{id}/leave")
	public User leaveChat(@DestinationVariable("id") long id) {
		return getCurrentUser();
	}

	/**
	 * 
	 * @param id
	 * @param user
	 * @param message
	 * @return
	 */
	@MessageMapping("/chat/{id}/message")
	@SendTo("/chat/{id}/message")
	public Message handleMessage(@DestinationVariable("id") long id, Message message) {
		message.setCreateDate(LocalDateTime.now());
		message.setUser(getCurrentUser());
		return message;
	}

}
