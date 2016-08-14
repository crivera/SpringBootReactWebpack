package com.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.model.User;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert insertUser;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.insertUser = new SimpleJdbcInsert(dataSource).withTableName("user").usingGeneratedKeyColumns("id");
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserByAccountKitId(long accountKitId) {
		return jdbcTemplate.queryForObject("select * from user where account_kit_id = ?", new Object[] { accountKitId },
				User.class);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public User createUser(User user) {
		Map<String, Object> parameters = new HashMap<>();
		if (StringUtils.isNotEmpty(user.getEmail()))
			parameters.put("email", user.getEmail());
		if (StringUtils.isNotEmpty(user.getPhone()))
			parameters.put("phone", user.getPhone());
		parameters.put("account_kit_id", user.getAccountKitId());
		
		parameters.put("enabled", true);
		parameters.put("create_date", LocalDateTime.now());
		parameters.put("last_update_date", LocalDateTime.now());
		Number id = insertUser.executeAndReturnKey(parameters);
		user.setId(id.longValue());
		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public User updateUser(User user) {
		return null;
	}
}