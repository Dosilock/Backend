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
}
