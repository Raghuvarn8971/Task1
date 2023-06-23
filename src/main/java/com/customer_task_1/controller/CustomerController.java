package com.customer_task_1.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer_task_1.Dto.CustomerReport;
import com.customer_task_1.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	 @Autowired
	    private CustomerService customerService;

	    @GetMapping("/report")
	    public ResponseEntity<List<CustomerReport>> generateReport() {
	        try {
	            List<CustomerReport> report = customerService.generateReport();
	            return new ResponseEntity<>(report, HttpStatus.OK);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).build();
	        }
	    }
	
}

