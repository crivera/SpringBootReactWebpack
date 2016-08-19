package com.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long accountKitId;
	private long facebookId;

	private String firstName;
	private String lastName;
	private String userName;

	private String phone;
	private String email;

	private String profilePicUrl;
	private boolean enabled;

	private LocalDateTime createDate;
	private LocalDateTime lastUpdateDate;

	private String token;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAccountKitId() {
		return accountKitId;
	}

	public void setAccountKitId(long accountKitId) {
		this.accountKitId = accountKitId;
	}

	public long getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(long facebookId) {
		this.facebookId = facebookId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * spring security stuff
	 */
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList("ROLE_USER");
		return roles;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return "notneeded";
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 
	 * @return
	 */
	@JsonIgnore
	public Map<String, ?> getParams() {
		Map<String, Object> parameters = new HashMap<>();
		if (StringUtils.isNotEmpty(this.getEmail()))
			parameters.put("email", this.getEmail());
		if (StringUtils.isNotEmpty(this.getPhone()))
			parameters.put("phone", this.getPhone());
		parameters.put("account_kit_id", this.getAccountKitId());

		parameters.put("enabled", this.isEnabled());
		parameters.put("create_date", this.getCreateDate());
		parameters.put("last_update_date", this.getLastUpdateDate());
		return parameters;
	}
	
}
