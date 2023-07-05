package com.corptec.etl.dataManipulation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {   
    
    private Long customerId;
    private String name;
    private String surname;
    private String gender;

    public Customer(Long customerId, String name, String surname, String gender) {
        this.customerId = customerId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
    }

    // Add default constructor for Jackson
    public Customer() {
    }

    @JsonProperty("Customer_ID")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @JsonProperty("Gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    

}
