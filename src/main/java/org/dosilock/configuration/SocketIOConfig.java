package org.dosilock.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;

@Configuration
public class SocketIOConfig {

	@Value("${spring.profiles.active}")
	private String acitveProfile;

	private String keyStorePassword = "1234";
	private String keyStoreType = "PKCS12";
	private String keyStroe = "/root/dosilock.kro.kr/key.p12";

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname("0.0.0.0");
		config.setPort(3700);

		// 배포 서버 확인
		if (acitveProfile.equals("prod")) {
			config.setKeyStorePassword(keyStorePassword);
			config.setKeyStoreFormat(keyStoreType);
			try {
				InputStream inputStream = new FileInputStream(keyStroe);
				config.setKeyStore(inputStream);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return new SocketIOServer(config);
	}
}
