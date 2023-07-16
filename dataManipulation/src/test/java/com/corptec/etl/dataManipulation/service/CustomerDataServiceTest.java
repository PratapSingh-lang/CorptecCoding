package com.corptec.etl.dataManipulation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.corptec.etl.dataManipulation.Repository.CustomerRepository;
import com.corptec.etl.dataManipulation.entity.CustomerData;
import com.corptec.etl.dataManipulation.service.Impl.CustomerDataServiceImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerDataServiceTest {

	
	@Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerDataServiceImplementation customerDataService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByRegion() {
        // Arrange
        String region = "Some Region";
        List<CustomerData> expectedData = new ArrayList<>();
        // Add expected data to the list

        // Mock the behavior of the customerRepository.findByRegion() method
        when(customerRepository.findByRegion(region)).thenReturn(expectedData);

        // Act
        List<CustomerData> result = customerDataService.findByRegion(region);

        // Assert
        assertEquals(expectedData, result);
        verify(customerRepository, times(1)).findByRegion(region);
    }

    @Test
    public void testFindByJobClassification() {
        // Arrange
        String jobClassification = "Some Job Classification";
        List<CustomerData> expectedData = new ArrayList<>();
        // Add expected data to the list

        // Mock the behavior of the customerRepository.findByJobClassification() method
        when(customerRepository.findByJobClassification(jobClassification)).thenReturn(expectedData);

        // Act
        List<CustomerData> result = customerDataService.findByJobClassification(jobClassification);

        // Assert
        assertEquals(expectedData, result);
        verify(customerRepository, times(1)).findByJobClassification(jobClassification);
    }

}
