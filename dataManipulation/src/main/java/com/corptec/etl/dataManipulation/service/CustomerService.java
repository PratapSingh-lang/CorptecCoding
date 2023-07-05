package com.corptec.etl.dataManipulation.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

//	void saveCustomersFromCsv(String csvFilePath, int batchSize) throws IOException;
	void importCustomersFromCSV(String csvFilePath);
	
}
