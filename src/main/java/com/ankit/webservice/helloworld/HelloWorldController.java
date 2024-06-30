/**
 * 
 */
package com.ankit.webservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for hello-world
 */
@RestController
public class HelloWorldController {
	
	@GetMapping("/hello")
	public String getHelloWorld() {
		return "Hello World";
	}

	@GetMapping("/hello-bean")
	public HelloWorldBean getHelloWorldBean() {
		HelloWorldBean resBean = HelloWorldBean.builder()
				.message("Message foe helloworld from lombok")
				.build();
		return resBean;
	}
	
	@GetMapping("/hello-bean/path-variable/{name}")
	public HelloWorldBean getHelloWorldPathVariable(@PathVariable String name) {
		HelloWorldBean resBean = HelloWorldBean.builder()
				.message("Message foe helloworld from lombok: "+name)
				.build();
		return resBean;
	}
	
}
