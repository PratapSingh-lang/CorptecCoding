package com.corptec.etl.dataManipulation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RootResponse {
	@JsonProperty("root")
    private Region region;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
