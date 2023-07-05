package com.corptec.etl.dataManipulation.helper;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Service
public class GenerateDataFromXmlFile {

	public MetaDetails getDataFromXML() {
		
		MetaDetails metaDetails = new MetaDetails();
		  File inputFile = new File("output.xml");
		 try {
	            // Create a DocumentBuilder
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();

	            // Parse the XML file and create a Document object
	            Document document = builder.parse(inputFile);

	            // Get the root element
	            Element rootElement = document.getDocumentElement();

	            // Get the connection element
	            Element connectionElement = (Element) rootElement.getElementsByTagName("Connection").item(0);

	            // Retrieve bucketName and objectName values
	            String bucketName = connectionElement.getElementsByTagName("BUCKET_NAME").item(0).getTextContent();
	            String objectName = connectionElement.getElementsByTagName("OBJECT_NAME").item(0).getTextContent();

	            // Get the file metadata element
	            Element fileMetadataElement = (Element) rootElement.getElementsByTagName("FileMetadata").item(0);

	            // Retrieve accessKey and secretKey values
	            String accessKey = fileMetadataElement.getElementsByTagName("ACCESS_KEY").item(0).getTextContent();
	            String secretKey = fileMetadataElement.getElementsByTagName("SECRET_KEY").item(0).getTextContent();

	            metaDetails.setACCESS_KEY(accessKey);
	            metaDetails.setBUCKET_NAME(bucketName);
	            metaDetails.setOBJECT_NAME(objectName);
	            metaDetails.setSECRET_KEY(secretKey);
	            
	            return metaDetails;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return metaDetails;
	}
		 
		 public String getBucketOrObjectDataFromXML(String key) {
				
			  File inputFile = new File("output.xml");
			 try {
		            // Create a DocumentBuilder
		            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder builder = factory.newDocumentBuilder();

		            // Parse the XML file and create a Document object
		            Document document = builder.parse(inputFile);

		            // Get the root element
		            Element rootElement = document.getDocumentElement();

		            // Get the connection element
		            Element connectionElement = (Element) rootElement.getElementsByTagName("Connection").item(0);

		            // Retrieve bucketName and objectName values
		            String bucketName = connectionElement.getElementsByTagName(key).item(0).getTextContent();
//		            String objectName = connectionElement.getElementsByTagName("OBJECT_NAME").item(0).getTextContent();
		            return bucketName;
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			return key;
		 }
		 
		 public String getAccessOrSecretKeysFromXML(String key) {
				
			  File inputFile = new File("output.xml");
			 try {
		            // Create a DocumentBuilder
		            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder builder = factory.newDocumentBuilder();

		            // Parse the XML file and create a Document object
		            Document document = builder.parse(inputFile);

		            // Get the root element
		            Element rootElement = document.getDocumentElement();
		  // Get the file metadata element
         Element fileMetadataElement = (Element) rootElement.getElementsByTagName("FileMetadata").item(0);

         // Retrieve accessKey and secretKey values
         String accessKey = fileMetadataElement.getElementsByTagName(key).item(0).getTextContent();
//         String secretKey = fileMetadataElement.getElementsByTagName("SECRET_KEY").item(0).getTextContent();
         	return accessKey;
			 } catch (Exception e) {
		            e.printStackTrace();
		        }
			return key;
		 }
}
