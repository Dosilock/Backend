package org.dosilock.configuration;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().print("{\"message\":\"success\"}");
		response.getWriter().flush();
	}
}
