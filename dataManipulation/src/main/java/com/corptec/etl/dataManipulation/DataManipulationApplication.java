package com.corptec.etl.dataManipulation;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.corptec.etl.dataManipulation.helper.CustomerDataGenerator;
import com.corptec.etl.dataManipulation.helper.GenerateXMLFile;
import com.corptec.etl.dataManipulation.helper.*;
import com.corptec.etl.dataManipulation.service.CustomerService;
import com.corptec.etl.dataManipulation.helper.GenerateDataFromXmlFile;
import com.corptec.etl.dataManipulation.service.Impl.CustomerServiceImplementation;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class DataManipulationApplication {

	@Autowired
	private SignedXMLGenerator SignedXMLGenerator;
	 @Autowired
	 private GenerateXMLFile generateXMLFile;
	
	 @Autowired
	 private CustomerDataGenerator customerDataGenerator;
	 @Autowired
	 private CustomerService customerService;
	 @Autowired
	 private CustomerServiceImplementation userService;
	 @Autowired
	 private GetDataFromS3  getDataFromS3;
	 
	public static void main(String[] args) {
		SpringApplication.run(DataManipulationApplication.class, args);
	}

	@PostConstruct
    public void init() {
		
        // Call the method to generate and save data to CSV
//        customerDataGenerator.generateAndSaveDataAsCSV(); 
		
		try {
			
			//Genearating an xml file to store meta details and storing it to local directory
			generateXMLFile.generateXmlFile();
			
			// this class will generate a signed xml if key details are provided
//			SignedXMLGenerator.generateSignedXml();
			
			
		// calling this class to retrive data from s3
			getDataFromS3.execute();
			
			
			// Dumping csv Data to postgres database 
			dumpToDb();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	
	public void dumpToDb() {

        File file = new File("customer.csv");
        // Call the method with the CSV file path 
        try {
        	userService.importCustomersFromCSV(file.getAbsolutePath());
            System.out.println("Data insertion completed successfully!");
        } 
        finally {
//            context.close();
        }
	}
	
}
