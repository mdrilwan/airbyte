package com.app.airbyte.controller;

import com.app.airbyte.dto.Response;
import com.app.airbyte.service.AppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app")
@Tag(name = "App", description = "Functionalities related to Airbyte App")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/listSources")
    public ResponseEntity<Response<String>> listSources() {
        Response<String> response = new Response<String>(
                "Sources fetched successfully",
                appService.listAllSources()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}
