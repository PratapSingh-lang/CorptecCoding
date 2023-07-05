package com.corptec.etl.dataManipulation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegionDetailsList {

	@JsonProperty("Region")
	List<RegionDetails> regionDetails;

	public List<RegionDetails> getRegionDetails() {
		return regionDetails;
	}

	public void setRegionDetails(List<RegionDetails> regionDetails) {
		this.regionDetails = regionDetails;
	}

	
}
