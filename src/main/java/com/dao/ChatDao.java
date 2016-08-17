package com.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.model.Chat;

@Repository
public class ChatDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert insertChat;

	private BeanPropertyRowMapper<Chat> chatMapper;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.insertChat = new SimpleJdbcInsert(dataSource).withTableName("chat").usingGeneratedKeyColumns("id");
		chatMapper = new BeanPropertyRowMapper<Chat>(Chat.class);
		chatMapper.setCheckFullyPopulated(false);
		chatMapper.setPrimitivesDefaultedForNullValue(true);
	}

	/**
	 * 
	 * @param chat
	 * @return
	 */
	public Chat save(Chat chat) {
		Number id = insertChat.executeAndReturnKey(chat.getParams());
		chat.setId(id.longValue());
		return chat;
	}

	/**
	 * 
	 * @param topRightLat
	 * @param topRightLng
	 * @param bottomLeftLat
	 * @param bottomLeftLng
	 * @return
	 */
	public List<Chat> getChatsInBounds(double topRightLat, double topRightLng, double bottomLeftLat,
			double bottomLeftLng) {
		return jdbcTemplate.query("select * from chat where lat <= ? and lng <= ? and lat >= ? and lng >= ? ", chatMapper, topRightLat, topRightLng, bottomLeftLat,
				bottomLeftLng);
	}

}
