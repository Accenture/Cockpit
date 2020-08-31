package com.cockpit.api.controller;

import java.util.List;

import com.cockpit.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.BurnUpChartDTO;
import com.cockpit.api.service.BurnUpChartService;

@RestController
@CrossOrigin
public class BurnUpChartController {

    private final BurnUpChartService chartService;
    private final AuthService authService;

    @Autowired
	public BurnUpChartController(BurnUpChartService chartService, AuthService authService) {
		this.chartService = chartService;
		this.authService = authService;
	}
    
    // GET Chart BY mvp ID
    @GetMapping(
            value = "/api/v1/burnUpChart/{id}"
    )
    public ResponseEntity getBurnUpChart(@PathVariable Long id,
                                 @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                List<BurnUpChartDTO> data = chartService.getChartData(id);
                return ResponseEntity.ok().body(data);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }
    
}

 
