package com.well.demo;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	
	private static final String templante = "Hello, %s!";
	private static final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam( value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(templante, name));
	}
}