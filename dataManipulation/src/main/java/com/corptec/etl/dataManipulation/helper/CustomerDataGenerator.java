/**
 * 
 */
package com.corptec.etl.dataManipulation.helper;

import com.corptec.etl.dataManipulation.entity.CustomerData;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

/**
 * @author bhanu.pratap
 *
 */
@Service
public class CustomerDataGenerator {

    private static final long initialCustomerId = 100000001;
    private static final List<String> names = List.of("John", "Emma", "Michael", "Sophia", "William", "Olivia");
    private static final List<String> surnames = List.of("Smith", "Johnson", "Brown", "Davis", "Miller", "Wilson");
    private static final List<String> regions = List.of("New York", "Los Angeles", "Chicago", "Houston", "Miami", "Seattle");
    private static final List<String> jobClassifications = List.of("Software Engineer", "Data Analyst", "Project Manager", "UX Designer", "Business Analyst");


    public void generateAndSaveDataAsCSV() {
        Random random = new Random();
        List<CustomerData> customers = new ArrayList<>();

        for (int i = 0; i < 5000 ; i++) {
            CustomerData customer = new CustomerData();
            
            customer.setCustomerId(initialCustomerId + i);
            customer.setName(getRandomElement(names, random));
            customer.setSurname(getRandomElement(surnames, random));
            customer.setGender(random.nextBoolean() ? "Male" : "Female");
            customer.setAge(18 + random.nextInt(53));
            customer.setRegion(getRandomElement(regions, random));
            customer.setJobClassification(getRandomElement(jobClassifications, random));
            customer.setDateJoined(new Date());
            customer.setBalance(1000 + random.nextDouble() * 99000);

            customers.add(customer);
        }
        

        // Generate CSV file
        String[] header = {"Customer_ID", "Name", "Surname", "Gender", "Age", "Region", "Job_Classification", "Date_Joined", "Balance"};
        String csvFile = "customer_data.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            writer.writeNext(header);
            for (CustomerData customer : customers) {
                writer.writeNext(new String[]{
                        String.valueOf(customer.getCustomerId()),
                        customer.getName(),
                        customer.getSurname(),
                        customer.getGender(),
                        String.valueOf(customer.getAge()),
                        customer.getRegion(),
                        customer.getJobClassification(),
                        customer.getDateJoined().toString(),
                        String.valueOf(customer.getBalance())
                });
            }
            System.out.println("CSV file generated successfully: " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private  String getRandomElement(List<String> list, Random random) {
        int index = random.nextInt(list.size());
        return list.get(index);
    }
    
}
