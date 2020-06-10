package com.cockpit.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.BurnUpChartDTO;

import com.cockpit.api.service.BurnUpChartService;

@RestController
public class BurnUpChartController {

    private final BurnUpChartService chartService;

    @Autowired
	public BurnUpChartController(BurnUpChartService chartService) {
		this.chartService = chartService;
	}
    
    // GET Chart BY mvp ID
    @GetMapping(
            value = "/api/v1/burnUpChart/{id}"
    )
    public ResponseEntity getMvp(@PathVariable Long id) {
        try {
        	List<BurnUpChartDTO> data = chartService.getChartData(id);
            return ResponseEntity.ok().body(data);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
}

 
