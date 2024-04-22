package org.dosilock.configuration;

import org.dosilock.jwt.JwtAuthenticationFilter;
import org.dosilock.jwt.JwtTokenProvider;
import org.dosilock.oauth.OAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final OAuth2UserService oAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic(HttpBasicConfigurer::disable)
			.csrf(CsrfConfigurer::disable)
			.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize ->
				authorize
					.anyRequest()
					.permitAll())
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/login") // 로그인 페이지 경로 설정 (선택 사항)
				.userInfoEndpoint(userInfo -> userInfo
					.userService(oAuth2UserService) // OAuth2UserService를 구현한 커스텀 서비스
				)
			)
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
