package com.deepak.study_related.ws.spring;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.study_related.ws.spring.model.Address;
import com.deepak.study_related.ws.spring.model.Greeting;

@RestController
public class SampleWsController {
	private static final Logger logger = LoggerFactory.getLogger(SampleWsController.class);
	

    private static final String template = "Hello, %s!";
    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	counter.incrementAndGet();
    	logger.info("Got a request with name {}, number {}", name, counter.get());
        return new Greeting(counter.get(),
                            String.format(template, name));
    }
    
    @RequestMapping("/address")
    public Address address(@RequestParam(value="houseName", defaultValue="Vento") String houseName) {
        counter.incrementAndGet();
        logger.info("Got a request with name {}, number {}", houseName, counter.get());
        return Address.sampleAddress(houseName, counter.get());
    }
}
