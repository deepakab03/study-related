package com.deepak.questions.int_q.hker_rnk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Assuming spring-boot-rest projects SampleWsControlleris running, and we access the address part
 * 
 * @author Deepak Abraham
 */
public class ParseHttpGetJsonRequest {
    private static final String HOUSE_NUMBER_STR = "houseNumber";
    private static final String CONTACT_INFO_STR = "contactInfo";
    private static final Logger logger = LoggerFactory.getLogger(ParseHttpGetJsonRequest.class);
    
    public String obtainContactHouseNumber() throws IOException {
        String response = connectAndObtainWebText();
        
        logger.debug("Got request value: {}", response);
        
        return parseContactHouseNumber(response);
    }

    private String connectAndObtainWebText() throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8085/address?name=Xan");
        URLConnection urlConn = url.openConnection();
//        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
//        urlConn.setRequestMethod("GET"); //optional
        
//        urlConn.setRequestProperty("Content-Type", 
//                "application/x-www-form-urlencoded");
//        urlConn.setRequestProperty("Content-Length", 
//                Integer.toString(urlParameters.getBytes().length));
//        urlConn.setRequestProperty("Content-Language", "en-US");  

        urlConn.connect();
        
      //Get Response  
        String response = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));) {
            response = reader.lines().collect(Collectors.joining("\n"));
        }
        //1.7 way
//        response = readJava7Way(urlConn);
        return response;
    }

    @SuppressWarnings("unused")
    private String readJava7Way(URLConnection urlConn) throws IOException {
        String response;
        InputStream is = urlConn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder responseSb = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
          responseSb.append(line);
          responseSb.append("\n");
        }
        rd.close();
        response = responseSb.toString();
        return response;
    }
    
    private String parseContactHouseNumber(String response) {
        int contactInfoIndex = response.indexOf(CONTACT_INFO_STR);
        if (contactInfoIndex == -1) {
            logger.info("No {} found", CONTACT_INFO_STR);
            return null;
        }
        int houseNumberIndex = response.indexOf(HOUSE_NUMBER_STR, contactInfoIndex);
        if (houseNumberIndex == -1) {
            logger.info("No {} found", HOUSE_NUMBER_STR);
            return null;
        }
        int nextCommaIndex = response.indexOf(",", houseNumberIndex);
        if (houseNumberIndex == -1) {
            logger.info("No next comma found");
            return null;
        }
        return response.substring(houseNumberIndex + HOUSE_NUMBER_STR.length() + 3, nextCommaIndex - 1);
    }
}
