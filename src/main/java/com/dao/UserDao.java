package com.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.model.User;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert insertUser;

	private BeanPropertyRowMapper<User> userMapper;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.insertUser = new SimpleJdbcInsert(dataSource).withTableName("user").usingGeneratedKeyColumns("id");
		userMapper = new BeanPropertyRowMapper<User>(User.class);
		userMapper.setCheckFullyPopulated(false);
		userMapper.setPrimitivesDefaultedForNullValue(true);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserByAccountKitId(long accountKitId) {
		return jdbcTemplate.queryForObject("select * from user where account_kit_id = ?", new Object[] { accountKitId },
				userMapper);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(long userId) {
		return jdbcTemplate.queryForObject("select * from user where id = ?", new Object[] { userId }, userMapper);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public User save(User user) {
		Number id = insertUser.executeAndReturnKey(user.getParams());
		user.setId(id.longValue());
		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public User update(User user) {
		return null;
	}

}
