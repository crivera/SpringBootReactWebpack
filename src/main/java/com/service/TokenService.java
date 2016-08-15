package com.service;

import java.security.Key;
import java.time.Clock;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

import com.model.User;
import com.utils.Constants;

@Service
public class TokenService {

	/**
	 * 
	 * @param user
	 * @return
	 * @throws JoseException
	 */
	public String createTokenForUser(User user) throws JoseException {
		Key key = new AesKey(Constants.KRYPTO_KEY.getBytes());
		JsonWebEncryption jwe = new JsonWebEncryption();
		jwe.setPayload(String.valueOf(user.getId()) + ":" + Clock.systemUTC().millis());
		jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A256KW);
		jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_256_CBC_HMAC_SHA_512);
		jwe.setKey(key);

		String serializedJwe = jwe.getCompactSerialization();
		return serializedJwe;
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws JoseException
	 */
	public String getUserFromToken(String token) throws JoseException {
		Key key = new AesKey(Constants.KRYPTO_KEY.getBytes());
		JsonWebEncryption jwe = new JsonWebEncryption();
		jwe = new JsonWebEncryption();
		jwe.setKey(key);
		jwe.setCompactSerialization(token);
		String payload = jwe.getPayload();
		return payload;
	}

}
