package com.corptec.etl.dataManipulation.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.corptec.etl.dataManipulation.entity.CustomerData;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerData, Long> {

	List<CustomerData> findByRegion(String region);

	List<CustomerData> findByJobClassification(String findByJobClassification);

}
