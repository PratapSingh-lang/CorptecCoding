package com.corptec.etl.dataManipulation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.corptec.etl.dataManipulation.entity.CustomerData;

@Service
public interface CustomerDataService {

	List<CustomerData> findByRegion(String region);

	List<CustomerData> findByJobClassification(String jobClassification);

}
