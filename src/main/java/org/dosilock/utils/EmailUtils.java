package org.dosilock.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailUtils {
	private final JavaMailSender emailSender;

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("gongsilock@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void sendSingupMessage(String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("gongsilock@gmail.com");
		message.setTo(to);
		message.setSubject("공시락 회원가입 이메일 인증 입니다.");
		message.setText("확인 버튼");
		emailSender.send(message);
	}
}
