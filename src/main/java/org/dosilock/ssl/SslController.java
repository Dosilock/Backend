package org.dosilock.ssl;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/.well-known/pki-validation")
public class SslController {
	private final ServletContext servletContext;

	@GetMapping
	public ResponseEntity<Resource> getImage(@PathVariable(value = "filename") String filename) throws IOException {
		if(filename.contains("..")) {
			return ResponseEntity.badRequest().build();
		}
		Path filePath = Paths.get("/var/www/html/.well-known/pki-validation/" + filename);
		Resource resource = new UrlResource(filePath.toUri());
		if(!resource.exists() || !resource.isReadable()) {
			return ResponseEntity.notFound().build();
		}

		String mimeType = servletContext.getMimeType(resource.getFile().getAbsolutePath());
		if(mimeType == null) {
			mimeType = "application/octet-stream";
		}
		return ResponseEntity
			.ok()
			.contentType(MediaType.parseMediaType(mimeType))
			.body(resource);
	}
}
