package org.dosilock.configuration;

import org.dosilock.configuration.login.CustomAccessDeniedHandler;
import org.dosilock.configuration.login.CustomAuthenticationFailureHandler;
import org.dosilock.configuration.login.CustomAuthenticationFilter;
import org.dosilock.configuration.login.CustomAuthenticationSuccessHandler;
import org.dosilock.configuration.login.CustomLoginAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	private final CustomLoginAuthenticationEntryPoint authenticationEntryPoint;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final CustomAccessDeniedHandler accessDeniedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic(HttpBasicConfigurer::disable)
			.csrf(CsrfConfigurer::disable)
			.cors(CorsConfigurer::disable)
			.authorizeHttpRequests(authorize ->
				authorize
					.requestMatchers("/api/v1/signup/**", "/api/v1/signin/**", "/api/v1/password/**")
					.permitAll()
					.anyRequest()
					.permitAll()
			)
			.addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(config -> config
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler));
		// .oauth2Login(Customizer.withDefaults());
		return httpSecurity.build();
	}

	@Bean
	public CustomAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
		customAuthenticationFilter.setAuthenticationManager(authenticationManager());
		customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
		customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

		// **
		customAuthenticationFilter.setSecurityContextRepository(
			new DelegatingSecurityContextRepository(
				new RequestAttributeSecurityContextRepository(),
				new HttpSessionSecurityContextRepository()
			));

		return customAuthenticationFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
