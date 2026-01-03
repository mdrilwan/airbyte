package com.app.airbyte.service;

import com.app.airbyte.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class AppService {

    @Autowired
    private RestTemplate restTemplate;

    private String getUserHeader() {
        String auth = Constants.AIRBYTE_USER_EMAIL + ":" + Constants.AIRBYTE_PASSWORD;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encodedAuth;
    }

    public String listAllSources() {
        String url = Constants.AIRBYTE_HOST + Constants.AIRBYTE_SOURCE_LIST_API_URL;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(MediaType.parseMediaTypes("application/json"));
        headers.set("Authorization", this.getUserHeader());

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        String response = restTemplate.postForObject(url, entity, String.class);

        return response;
    }

    public String listAllDest() {
        String url = Constants.AIRBYTE_HOST + Constants.AIRBYTE_DEST_LIST_API_URL;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(MediaType.parseMediaTypes("application/json"));
        headers.set("Authorization", this.getUserHeader());

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        String response = restTemplate.postForObject(url, entity, String.class);

        return response;
    }
}
