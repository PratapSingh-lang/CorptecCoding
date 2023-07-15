package com.corptec.etl.dataManipulation.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corptec.etl.dataManipulation.dto.*;
import com.corptec.etl.dataManipulation.entity.CustomerData;
import com.corptec.etl.dataManipulation.helper.CreateResponseData;
import com.corptec.etl.dataManipulation.service.CustomerDataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path = "/customerDetails")
@CrossOrigin("*")
public class CustomerController {

	@Autowired
	private CustomerDataService customerDataService;
	@Autowired
	private CreateResponseData createResponseData;

	 private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@GetMapping(value = "/{name}/region", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getCustomersByRegion(@PathVariable String name) {

		List<CustomerData> customers = customerDataService.findByRegion(name);
		RootResponse response = createResponseData.createCutomerResponse(customers, name);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/{name}/classification", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getCustomersByJobClassification(@PathVariable String name) {
		List<CustomerData> customers = customerDataService.findByJobClassification(name);

		RootResponseForClassification createCutomerResponse = createResponseData.createCutomerResponse(customers);
		return new ResponseEntity<>(createCutomerResponse, HttpStatus.OK);
	}

}
