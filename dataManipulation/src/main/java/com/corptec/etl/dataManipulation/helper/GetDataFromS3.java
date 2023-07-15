package com.corptec.etl.dataManipulation.helper;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.corptec.etl.dataManipulation.controller.CustomerController;
import com.corptec.etl.dataManipulation.entity.CustomerData;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.InputStream;

@Service
public class GetDataFromS3 {

	 private static final Logger log = LoggerFactory.getLogger(GetDataFromS3.class);
	
	 @Autowired
	 private GenerateDataFromXmlFile generateDataFromXmlFile;
	public void execute() throws IOException {
        
		log.info("TalendJob: 'LoadFileDataFromS3ToPostgres' - Start.");
		
		MetaDetails metaDetails = generateDataFromXmlFile.getDataFromXML();
		
		log.info("ContextLoad_1 - Loaded contexts count: 13.");
		
		log.info("tS3Connection_1 - Creating new connection.");

//        //1 - Setup S3 Client
        AWSCredentials credentials = new BasicAWSCredentials(metaDetails.getACCESS_KEY(), metaDetails.getSECRET_KEY());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();

        log.info("tS3Connection_1 - Creating new connection successfully.");
        log.info("tS3Get_1 - Get an free connection from tS3Connection_1.");
        
        //2 - Get Object from S3 - Option 1 - Write to Disk
        GetObjectRequest request = new GetObjectRequest(metaDetails.getBUCKET_NAME(), metaDetails.getOBJECT_NAME());
        File newFile = new File("customer.csv");

        log.info("tS3Get_1 - Getting an object with key:AUTO_DEPLOYMENT/new_cluster/Customer.csv");
        s3Client.getObject(request, newFile);

        log.info("tS3Get_1 - Get the object successfully.");
        

        log.info("S3FileDownloadAndStageToDirectory' is done.");

    }
	
}
