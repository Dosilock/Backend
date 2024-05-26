package org.dosilock.socket;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/socket")
public class SocketController {

	private final SocketService socketService;

	@PostMapping(value = "/{name}")
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
}
