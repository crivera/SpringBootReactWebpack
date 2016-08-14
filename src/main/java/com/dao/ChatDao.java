package com.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert insertChat;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.insertChat = new SimpleJdbcInsert(dataSource).withTableName("chat").usingGeneratedKeyColumns("id");
	}

}
