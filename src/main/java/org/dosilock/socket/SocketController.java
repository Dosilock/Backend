package org.dosilock.socket;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/socket/")
public class SocketController {

	private final SocketService socketService;

	@PostMapping("/{name}")
	public ResponseEntity<Void> createNamespace(@PathVariable("name") String name) {
		socketService.addNamespace(name);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/server/time")
	public ResponseEntity<Map<String, String>> getServerTime() {
		Map<String, String> response = new HashMap<>();
		String serverTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		response.put("serverTime", serverTime);
		return ResponseEntity.ok(response);
	}

	@GetMapping("sessionid")
	public ResponseEntity<Map<String, String>> getCookie(HttpServletRequest request) {
		Map<String, String> cookie = new HashMap<>();
		cookie.put("sessionId", request.getSession().getId());
		return ResponseEntity.ok(cookie);
	}
}
