package org.dosilock.socket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/socket/")
public class SocketController {

	private final SocketService socketService;

	@PostMapping("{name}")
	public ResponseEntity<Void> createNamespace(@PathVariable("name") String name) {
		socketService.addNamespace(name);
		return ResponseEntity.ok().build();
	}

	@GetMapping("sessionid")
	public ResponseEntity<Map<String, String>> getCookie(HttpServletRequest request) {
		Map<String, String> cookie = new HashMap<>();
		cookie.put("sessionId", request.getSession().getId());
		return ResponseEntity.ok(cookie);
	}
}
