package com.corptec.etl.dataManipulation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Region {
	 
	 @JsonProperty("Region")
	    private RegionDetails regionDetails;

	    public RegionDetails getRegionDetails() {
	        return regionDetails;
	    }

	    public void setRegionDetails(RegionDetails regionDetails) {
	        this.regionDetails = regionDetails;
	    }
}
