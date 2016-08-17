package com.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class Chat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private double lat;
	private double lng;

	private int currentUserCount;
	private int totalUserCount;

	private boolean enabled;

	private LocalDateTime createDate;
	private LocalDateTime lastUpdateDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getCurrentUserCount() {
		return currentUserCount;
	}

	public void setCurrentUserCount(int currentUserCount) {
		this.currentUserCount = currentUserCount;
	}

	public int getTotalUserCount() {
		return totalUserCount;
	}

	public void setTotalUserCount(int totalUserCount) {
		this.totalUserCount = totalUserCount;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	/**
	 * this is done so that you can choose which fields to return
	 * 
	 * @return
	 */
	public JSONObject toJSONObject() {
		return new JSONObject(this);
	}

	/**
	 * 
	 * @return
	 */
	public AppError validate() {
		if (StringUtils.isEmpty(this.getName()))
			return AppError.create(100, "Name cannot be empty.");
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, ?> getParams() {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", this.getName());
		parameters.put("lat", this.getLat());
		parameters.put("lng", this.getLng());
		parameters.put("current_user_count", this.getCurrentUserCount());
		parameters.put("total_user_count", this.getTotalUserCount());
		parameters.put("enabled", this.isEnabled());
		parameters.put("create_date", this.getCreateDate());
		parameters.put("last_update_date", this.getLastUpdateDate());
		return parameters;

	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
