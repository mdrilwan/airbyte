package com.app.airbyte.service;

import com.app.airbyte.config.Constants;
import com.app.airbyte.dto.ConnectorDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AppService {

    @Autowired
    private RestTemplate restTemplate;

    private String getUserHeader() {
        String auth = Constants.AIRBYTE_USER_EMAIL + ":" + Constants.AIRBYTE_PASSWORD;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encodedAuth;
    }

    public List<ConnectorDetails> listAllSources(Boolean includeEnterpriseSources, Boolean includeMarketplaceSources) throws JsonProcessingException {
        String url = Constants.AIRBYTE_HOST + Constants.AIRBYTE_SOURCE_LIST_API_URL;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(MediaType.parseMediaTypes("application/json"));
        headers.set("Authorization", this.getUserHeader());

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        String response = restTemplate.postForObject(url, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);

        List<ConnectorDetails> result = new ArrayList<ConnectorDetails>();
        for (JsonNode sourceDefinition : jsonNode.get("sourceDefinitions")) {
            boolean isEnterprise = sourceDefinition.get("enterprise").asBoolean();
            boolean isMarketplace = sourceDefinition.get("supportLevel").asText().equals("community");
            boolean exclude = false;
            if (isEnterprise && !includeEnterpriseSources) {
                exclude = true;
            }
            if(isMarketplace && !includeMarketplaceSources) {
                exclude = true;
            }
            if (!exclude) {
                ConnectorDetails sourceDetails = new ConnectorDetails(
                        sourceDefinition.get("sourceDefinitionId").asText(),
                        sourceDefinition.get("name").asText(),
                        sourceDefinition.get("enterprise").asBoolean(),
                        sourceDefinition.get("supportLevel").asText().equals("community"));
                result.add(sourceDetails);
            }
        }
        return result;
    }

    public List<ConnectorDetails> listAllDest(Boolean includeEnterpriseSources, Boolean includeMarketplaceSources) throws JsonProcessingException {
        String url = Constants.AIRBYTE_HOST + Constants.AIRBYTE_DEST_LIST_API_URL;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(MediaType.parseMediaTypes("application/json"));
        headers.set("Authorization", this.getUserHeader());

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        String response = restTemplate.postForObject(url, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);

        List<ConnectorDetails> result = new ArrayList<ConnectorDetails>();
        for (JsonNode sourceDefinition : jsonNode.get("destinationDefinitions")) {
            boolean isEnterprise = sourceDefinition.get("enterprise").asBoolean();
            boolean isMarketplace = sourceDefinition.get("supportLevel").asText().equals("community");
            boolean exclude = false;
            if (isEnterprise && !includeEnterpriseSources) {
                exclude = true;
            }
            if(isMarketplace && !includeMarketplaceSources) {
                exclude = true;
            }
            if (!exclude) {
                ConnectorDetails sourceDetails = new ConnectorDetails(
                        sourceDefinition.get("destinationDefinitionId").asText(),
                        sourceDefinition.get("name").asText(),
                        sourceDefinition.get("enterprise").asBoolean(),
                        sourceDefinition.get("supportLevel").asText().equals("community"));
                result.add(sourceDetails);
            }
        }
        return result;
    }

    public String getSourceConfigByDefinitionId(String sourceDefinitionId)
            throws JsonProcessingException {

        String url = Constants.AIRBYTE_HOST + Constants.AIRBYTE_SOURCE_CONFIG_API_URL;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(MediaType.parseMediaTypes("application/json"));
        headers.set("Authorization", this.getUserHeader());

        Map<String, String> body = new HashMap<>();
        body.put("sourceDefinitionId", sourceDefinitionId);
        body.put("workspaceId", Constants.AIRBYTE_WORKSPACE_ID);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, entity, String.class);
    }

    public @Nullable String getDestinationConfigByDefinitionId(String destinationDefinitionId)
            throws JsonProcessingException {

        String url = Constants.AIRBYTE_HOST + Constants.AIRBYTE_DEST_CONFIG_API_URL;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(MediaType.parseMediaTypes("application/json"));
        headers.set("Authorization", this.getUserHeader());

        Map<String, String> body = new HashMap<>();
        body.put("destinationDefinitionId", destinationDefinitionId);
        body.put("workspaceId", Constants.AIRBYTE_WORKSPACE_ID);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, entity, String.class);
    }

}
