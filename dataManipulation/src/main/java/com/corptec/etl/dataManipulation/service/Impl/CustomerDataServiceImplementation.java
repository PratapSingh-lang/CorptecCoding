package com.corptec.etl.dataManipulation.service.Impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corptec.etl.dataManipulation.Repository.CustomerRepository;
import com.corptec.etl.dataManipulation.entity.CustomerData;
import com.corptec.etl.dataManipulation.service.CustomerDataService;

@Service
public class CustomerDataServiceImplementation implements CustomerDataService{

	 private static final Log log = LogFactory.getLog(CustomerDataServiceImplementation.class);
	@Autowired
	private CustomerRepository customerRepository;
	@Override
	public List<CustomerData> findByRegion(String region) {
		
		log.info("returning customer data by region : " + region);
		List<CustomerData> customerDataByRegion = customerRepository.findByRegion(region);
		return customerDataByRegion;
	}

	@Override
	public List<CustomerData> findByJobClassification(String findByJobClassification) {

		log.info("returning customer data by JobClassification : " + findByJobClassification);
		List<CustomerData> customerDataByJobClassification = customerRepository.findByJobClassification(findByJobClassification);
		return customerDataByJobClassification;
	}

}
