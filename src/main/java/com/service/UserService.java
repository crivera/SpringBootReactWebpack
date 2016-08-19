package com.service;

import java.time.LocalDateTime;

import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.exception.GeneralError;
import com.model.User;

@Service
public class UserService {

	// private Logger log = Logger.getLogger(UserService.class);

	@Autowired
	TokenService tokenService;

	@Autowired
	UserDao userDao;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws GeneralError
	 */
	public User create(User user) throws GeneralError {
		user.setEnabled(true);
		user.setCreateDate(LocalDateTime.now());
		user.setLastUpdateDate(LocalDateTime.now());
		user = userDao.save(user);
		try {
			user.setToken(tokenService.createTokenForUser(user));
		} catch (JoseException e) {
			throw new GeneralError(e);
		}
		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws OnAireException
	 */
	public User updateUser(User user) {
		user.setLastUpdateDate(LocalDateTime.now());
		return userDao.update(user);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws GeneralError
	 */
	public User getUserById(long userId) throws GeneralError {
		if (userId <= 0)
			return null;
		User user = userDao.getUserById(userId);
		try {
			user.setToken(tokenService.createTokenForUser(user));
		} catch (JoseException e) {
			throw new GeneralError(e);
		}
		return user;
	}

	/**
	 * 
	 * @param accountKitId
	 * @return
	 * @throws GeneralError
	 */
	public User getUserByAccountKitId(long accountKitId) throws GeneralError {
		if (accountKitId <= 0)
			return null;
		User user = userDao.getUserByAccountKitId(accountKitId);
		try {
			user.setToken(tokenService.createTokenForUser(user));
		} catch (JoseException e) {
			throw new GeneralError(e);
		}
		return user;
	}

}
