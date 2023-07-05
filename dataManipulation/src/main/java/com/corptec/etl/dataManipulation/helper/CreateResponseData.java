package com.corptec.etl.dataManipulation.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.corptec.etl.dataManipulation.dto.*;
import com.corptec.etl.dataManipulation.entity.CustomerData;

@Service
public class CreateResponseData {

	public RootResponse createCutomerResponse(List<CustomerData> customerDataList, String name) {
     		
        // Create the response object
        RootResponse response = new RootResponse();

        // Create the region object
        Region region = new Region();
        RegionDetails regionDetails = new RegionDetails();
        regionDetails.setName(name);

        // Map customer data to customer objects
        List<Customer> customers = new ArrayList<>();
        for (CustomerData customerData : customerDataList) {
            Customer customer = new Customer();
            customer.setCustomerId(customerData.getCustomerId());
            customer.setName(customerData.getName());
            customer.setSurname(customerData.getSurname());
            customer.setGender(customerData.getGender());
            customers.add(customer);
        }
        regionDetails.setCustomer(customers);

        region.setRegionDetails(regionDetails);

        // Set the region in the response
        response.setRegion(region);

        return response;

	}

	public RootResponseForClassification createCutomerResponse(List<CustomerData> customerDataList) {
 				
		// Group customers by region
		Map<String, List<CustomerData>> groupedData = customerDataList.stream()
	            .collect(Collectors.groupingBy(CustomerData::getRegion));

		RegionDetailsList regionDetailsList = new RegionDetailsList();
		List<RegionDetails> list = new ArrayList<>();
	    // Create region customers response
	    for (Map.Entry<String, List<CustomerData>> entry : groupedData.entrySet()) {
	        String name = entry.getKey();
	        List<CustomerData> customersInRegion = entry.getValue();

	        List<Customer> customers = customersInRegion.stream()
	                .map(this::convertCustomerDataToCustomer)
	                .collect(Collectors.toList());

	        RegionDetails regionDetails = new RegionDetails();
	        regionDetails.setCustomer(customers);
	        regionDetails.setName(name);
	        
	        list.add(regionDetails);
	       
	    }
	    regionDetailsList.setRegionDetails(list);
	    
	    RootResponseForClassification rootResponse = new RootResponseForClassification();
	    rootResponse.setRegionDetailsList(regionDetailsList);
		return rootResponse;
	}
	
	private Customer convertCustomerDataToCustomer(CustomerData customerData) {
	    Customer customer = new Customer();
	    customer.setCustomerId(customerData.getCustomerId());
	    customer.setName(customerData.getName());
	    customer.setSurname(customerData.getSurname());
	    customer.setGender(customerData.getGender());
	    return customer;
	}

}
