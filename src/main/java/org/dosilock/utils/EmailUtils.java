package org.dosilock.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailUtils {
	private final JavaMailSender emailSender;

	private void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("gongsilock@gmail.com"); // 공통으로 사용되는 발신자 주소
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void sendSignupMessage(String to, String randomLinkCode) {
		String subject = "공시락 회원가입 이메일 인증 입니다.";
		String text = "회원가입 확인 링크: " + randomLinkCode;
		sendEmail(to, subject, text);
	}

	public void sendChangePasswordMessage(String to, String randomLinkCode) {
		String subject = "공시락 비밀번호 변경 이메일 인증 입니다.";
		String text = "비밀번호 변경 확인 링크: " + randomLinkCode;
		sendEmail(to, subject, text);
	}
}
