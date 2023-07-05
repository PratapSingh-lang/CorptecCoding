package com.corptec.etl.dataManipulation.helper;

public class MetaDetails {
	private String BUCKET_NAME ;
    private String OBJECT_NAME ; 
    private String ACCESS_KEY ;
    private String SECRET_KEY ;
	public String getBUCKET_NAME() {
		return BUCKET_NAME;
	}
	public void setBUCKET_NAME(String bUCKET_NAME) {
		BUCKET_NAME = bUCKET_NAME;
	}
	public String getOBJECT_NAME() {
		return OBJECT_NAME;
	}
	public void setOBJECT_NAME(String oBJECT_NAME) {
		OBJECT_NAME = oBJECT_NAME;
	}
	public String getACCESS_KEY() {
		return ACCESS_KEY;
	}
	public void setACCESS_KEY(String aCCESS_KEY) {
		ACCESS_KEY = aCCESS_KEY;
	}
	public String getSECRET_KEY() {
		return SECRET_KEY;
	}
	public void setSECRET_KEY(String sECRET_KEY) {
		SECRET_KEY = sECRET_KEY;
	}
    
    
}
