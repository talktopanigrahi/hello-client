package com.prakash.cloud.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

;

@RestController
//@RequestMapping("/rest/hello/client")
public class HelloResource {

	@Autowired
	private RestTemplate restTemplate;

	private static Logger logger = LoggerFactory.getLogger(HelloResource.class);

	@HystrixCommand(fallbackMethod = "fallback", groupKey = "Hello", commandKey = "hello", threadPoolKey = "helloThread")

	@GetMapping(value = "/rest/hello/cclient")
	public String hello() {
		//"http://localhost:8070/rest/hello/server"
		String url = "http://hello-server/rest/hello/server";
		logger.info("pkp Before Calling hello-server");

		String resp = restTemplate.getForObject(url, String.class);
		logger.info("pkp After Calling hello-server..");
		return resp + " - Includeing Client";
	}

	public String fallback(Throwable hystrixCommand) {
		return "Fall Back Hello world";
	}

}
