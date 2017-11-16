package com.deepak.questions.int_q.hker_rnk;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scenario is that we want to make a web-service GET request call, get some JSON, parse some content from it and return
 * the same
 * 
 * @author Deepak Abraham
 */
public class ParseHttpGetJsonRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(ParseHttpGetJsonRequestTest.class);
    
    @Test public void
    testObtainContactHouseNumber() throws IOException {
        ParseHttpGetJsonRequest request = new ParseHttpGetJsonRequest();
        
        String val = request.obtainContactHouseNumber();
        
        logger.info("value: {}", val);
        assertThat(val, is(equalTo("9723972")));
    }
}
