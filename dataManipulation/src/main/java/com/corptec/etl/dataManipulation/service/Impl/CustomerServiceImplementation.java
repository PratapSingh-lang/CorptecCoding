package com.corptec.etl.dataManipulation.service.Impl;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corptec.etl.dataManipulation.Repository.CustomerRepository;
import com.corptec.etl.dataManipulation.entity.CustomerData;
//import com.opencsv.CSVParser;
import com.corptec.etl.dataManipulation.service.CustomerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImplementation implements CustomerService {

    private static final int BATCH_SIZE = 1000;
    private static final Log log = LogFactory.getLog(CustomerServiceImplementation.class);
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void importCustomersFromCSV(String csvFilePath) {
    	
    	log.info("TalendJob: 'LocalFileStageToSql' - Start");
    	 Reader reader = null;
    	    CSVParser csvParser = null;

    	    try {
    	        reader = new FileReader(csvFilePath);
    	        csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            List<CustomerData> customersToInsert = new ArrayList<>();
            int rowCount = 0;

            log.info("tFileInputDelimited_2 - Retrieving records from the datasource.");
            
            for (CSVRecord record : csvParser) {
                Long customerId = Long.parseLong(record.get("Customer_ID"));
                String name = record.get("Name");
                String surname = record.get("Surname");
                String gender = record.get("Gender");
                int age = Integer.parseInt(record.get("Age"));
                String region = record.get("Region");
                String jobClassification = record.get("Job_Classification");
                Date dateJoined = parseDate(record.get("Date_Joined"));
                double balance = Double.parseDouble(record.get("Balance"));

                if (!isCustomerAlreadyExists(customerId)) {
                    CustomerData customer = new CustomerData();
                    customer.setCustomerId(customerId);
                    customer.setName(name);
                    customer.setSurname(surname); 
                    customer.setGender(gender);
                    customer.setAge(age);
                    customer.setRegion(region);
                    customer.setJobClassification(jobClassification);
                    customer.setDateJoined(dateJoined);
                    customer.setBalance(balance);
                    customersToInsert.add(customer);
                    rowCount++;

                    if (rowCount % BATCH_SIZE == 0) {
                    	log.info("Saving customer data to database in BATCH_SIZE of : "+ BATCH_SIZE);
                        customerRepository.saveAll(customersToInsert);
                        customersToInsert.clear();
                    }
                }
            }

            if (!customersToInsert.isEmpty()) {
                customerRepository.saveAll(customersToInsert);
            }

            log.info("tFileInputDelimited_2 - Retrieved records count: "+ rowCount);

            log.info("TalendJob: 'LoadFileDataFromS3ToPostgres' - Done.");
            
            //            System.out.println("Total customers inserted: " + rowCount);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvParser != null) {
                try {
                    csvParser.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isCustomerAlreadyExists(Long customerId) {
        Optional<CustomerData> existingCustomer = customerRepository.findById(customerId);
        return existingCustomer.isPresent();
    }

    private Date parseDate(String dateString) {
        try {
        	  SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
              return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
