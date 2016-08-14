package com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.exception.ErrorLoggingInException;
import com.model.User;
import com.utils.Constants;

@Service
public class FacebookService {

	@Value("${config.facebook.accountKit.appId}")
	private String appId;

	@Value("${config.facebook.accountKit.appSecret}")
	private String appSecret;

	@Autowired
	private UserDao userDao;

	/**
	 * 
	 * @param accessCode
	 * @return
	 * @throws ErrorLoggingInException
	 */
	public User loginWithAccountKit(String accessCode) throws ErrorLoggingInException {
		// so first we do a call against the token exchange url of accountKit
		HttpClient httpclient = HttpClientBuilder.create().build();
		List<BasicNameValuePair> list = new ArrayList<>();
		list.add(new BasicNameValuePair("grant_type", "authorization_code"));
		list.add(new BasicNameValuePair("access_token", "AA|" + appId + "|" + appSecret));
		list.add(new BasicNameValuePair("code", accessCode));

		HttpGet exchangeTokensGet = new HttpGet(
				Constants.ACCOUNT_KIT_TOKEN_EXCHANGE_URL + "?" + URLEncodedUtils.format(list, "UTF-8"));
		try {
			HttpResponse result = httpclient.execute(exchangeTokensGet);

			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(result.getEntity());
				JSONObject json = new JSONObject(response);
				long accountKitId = json.getLong("id");
				String accessToken = json.getString("access_token");
				String proof = generateAppSecretProof(accessToken, appSecret);

				HttpGet meGet = new HttpGet(
						Constants.ACCOUNT_KIT_ME_URL + "?access_token=" + accessToken + "&appsecret_proof=" + proof);
				result = httpclient.execute(meGet);
				if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					response = EntityUtils.toString(result.getEntity());
					json = new JSONObject(response);
					try {
						User user = userDao.getUserByAccountKitId(accountKitId);
						if (!user.isEnabled())
							throw new ErrorLoggingInException("User not enabled.");
						return user;
					} catch (DataAccessException e) {
						// this code is executed if we dont have the user in the
						// database yet
						if (e instanceof EmptyResultDataAccessException) {
							User user = new User();
							user.setAccountKitId(accountKitId);
							if (json.has("phone")){
								user.setPhone(json.getJSONObject("phone").getString("number"));
							}
							if (json.has("email")){
								user.setEmail(json.getJSONObject("email").getString("email"));
							}
							
							
							return userDao.createUser(user);
						} else
							throw new ErrorLoggingInException(e);
					}
				} else {
					// this error is executed if there is something wrong with
					// getting your personal info
					response = EntityUtils.toString(result.getEntity());
					json = new JSONObject(response);
					throw new ErrorLoggingInException(json.getJSONObject("error").getString("message"));
				}
			} else {
				// this code is executed if there is an error in doing the token
				// exchange
				String response = EntityUtils.toString(result.getEntity());
				JSONObject json = new JSONObject(response);
				throw new ErrorLoggingInException(json.getJSONObject("error").getString("message"));
			}
		} catch (IOException e) {
			// this code is executed if there is a network error
			throw new ErrorLoggingInException(e);
		}
	}

	/**
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	private String generateAppSecretProof(String data, String key) {
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance(Constants.HMAC_SHA_256);
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), Constants.HMAC_SHA_256);
			mac.init(secretKeySpec);
			byteHMAC = mac.doFinal(data.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder result = new StringBuilder();
		for (byte b : byteHMAC) {
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		String appSecretProof = result.toString();
		return appSecretProof;
	}

}
