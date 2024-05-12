package org.dosilock.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@CrossOrigin
public class TestController {

	@GetMapping()
	public String mainPage() {
		return "index";
	}
}
