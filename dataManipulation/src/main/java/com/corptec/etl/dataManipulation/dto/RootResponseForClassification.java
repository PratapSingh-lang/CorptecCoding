package com.corptec.etl.dataManipulation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RootResponseForClassification {


	@JsonProperty("root")
	RegionDetailsList regionDetailsList;
	public void setRegionDetailsList(RegionDetailsList regionDetailsList) {
		this.regionDetailsList = regionDetailsList;
	}


}
