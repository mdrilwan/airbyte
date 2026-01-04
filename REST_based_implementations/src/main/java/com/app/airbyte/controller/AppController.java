package com.app.airbyte.controller;

import com.app.airbyte.dto.Response;
import com.app.airbyte.dto.ConnectorDetails;
import com.app.airbyte.service.AppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app")
@Tag(name = "App", description = "Functionalities related to Airbyte App")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/listSources")
    public ResponseEntity<Response<List<ConnectorDetails>>> listSources(
            @RequestParam(name = "includeEnterpriseSources", required = false) Boolean includeEnterpriseSources,
            @RequestParam(name = "includeMarketplaceSources", required = false) Boolean includeMarketplaceSources
    ) {
        if(includeEnterpriseSources == null) {
            includeEnterpriseSources = false;
        }
        if(includeMarketplaceSources == null) {
            includeMarketplaceSources = false;
        }
        Response<List<ConnectorDetails>> response = null;
        try {
            response = new Response<List<ConnectorDetails>>(
                    "Sources fetched successfully",
                    appService.listAllSources(includeEnterpriseSources, includeMarketplaceSources)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/listDestinations")
    public ResponseEntity<Response<List<ConnectorDetails>>> listDestinations(
            @RequestParam(name = "includeEnterpriseSources", required = false) Boolean includeEnterpriseSources,
            @RequestParam(name = "includeMarketplaceSources", required = false) Boolean includeMarketplaceSources
    ) {
        if(includeEnterpriseSources == null) {
            includeEnterpriseSources = false;
        }
        if(includeMarketplaceSources == null) {
            includeMarketplaceSources = false;
        }
        Response<List<ConnectorDetails>> response = null;
        try {
            response = new Response<List<ConnectorDetails>>(
                    "Destinations fetched successfully",
                    appService.listAllDest(includeEnterpriseSources, includeMarketplaceSources)
                );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/source/config")
    public ResponseEntity<Response<String>> getSourceConfig(
            @RequestParam String sourceDefinitionId
    ) {
        try {
            Response<String> response = new Response<>(
                    "Source configuration fetched successfully",
                    appService.getSourceConfigByDefinitionId(sourceDefinitionId)
            );
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/destination/config")
    public ResponseEntity<Response<String>> getDestinationConfig(
            @RequestParam String destinationDefinitionId
    ) {
        try {
            Response<String> response = new Response<>(
                    "Destination configuration fetched successfully",
                    appService.getDestinationConfigByDefinitionId(destinationDefinitionId)
            );
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
