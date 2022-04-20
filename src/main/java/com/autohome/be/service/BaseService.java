package com.autohome.be.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BaseService {

    @Autowired
    protected RestTemplate restTemplate;

    @Value("${isp.auth.url}")
    protected String ispAuthUrl;

    @Value("${isp.api.url}")
    protected String ispApiUrl;

    protected String restGet(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            log.info("GET Request to: {}", url);
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (log.isDebugEnabled()) {
                log.debug("GET Response from: {} \nresData: {}", url, result);
            }
            return result.getBody();
        } catch (Exception ex) {
            log.error("Exception GET Request to: {}", url, ex);
            return null;
        }
    }

    protected String restPost(String url, String postObject) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(postObject, headers);
            if (log.isDebugEnabled()) {
                log.debug("POST Request to: {} \nreqData: {}", url, postObject);
            } else {
                log.info("POST Request to: {}", url);
            }
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (log.isDebugEnabled()) {
                log.debug("POST Response from: {} \nresData: {}", url, result);
            }
            return result.getBody();
        } catch (Exception ex) {
            log.error("Exception POST Request to: {}", url, ex);
            return null;
        }
    }
}
