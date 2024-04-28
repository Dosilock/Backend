package org.dosilock.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class InviteLink {

	private String generateInviteLink() throws Exception {
		String data = "clazz" + System.currentTimeMillis();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
		return Base64.getUrlEncoder().encodeToString(hash);
	}

	public String createInveteLink() throws Exception {
		return generateInviteLink();
	}
}
