package com.app.airbyte.controller;

import com.app.airbyte.service.AppService;
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

    @Autowired
    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/listSources")
    public ResponseEntity<String> listSources() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(appService.listAllSources());

    }
}
