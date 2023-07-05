package com.corptec.etl.dataManipulation.dto;

import java.util.List;

public class RegionDetails {

	private String name;
    private List<Customer> customer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }
}
