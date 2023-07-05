package com.corptec.etl.dataManipulation.helper;

import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

@Service
public class GenerateXMLFile {
	// Extract file metadata details
	private static final String bucketName = "cutomeretl";
	private static final String objectName = "customer_data.csv";
	private static final String accessKey = "AKIAQRMXFYTNLSW3K3FN";
	private static final String secretKey = "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";

	public void generateXmlFile() {

		// Generate XML document
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Create root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Metadata");
			doc.appendChild(rootElement);

			// Create connection element
			Element connectionElement = doc.createElement("Connection");
			rootElement.appendChild(connectionElement);

			// Create bucketName element
			Element bucketNameElement = doc.createElement("BUCKET_NAME");
			bucketNameElement.setTextContent(bucketName);
			connectionElement.appendChild(bucketNameElement);

			// Create objectName element
			Element objectNameElement = doc.createElement("OBJECT_NAME");
			objectNameElement.setTextContent(objectName);
			connectionElement.appendChild(objectNameElement);

			// Create file metadata element
			Element fileMetadataElement = doc.createElement("FileMetadata");
			rootElement.appendChild(fileMetadataElement);

			// Create accessKey element
			Element accessKeyElement = doc.createElement("ACCESS_KEY");
			accessKeyElement.setTextContent(accessKey);
			fileMetadataElement.appendChild(accessKeyElement);

			// Create secretKey element
			Element secretKeyElement = doc.createElement("SECRET_KEY");
			secretKeyElement.setTextContent(secretKey);
			fileMetadataElement.appendChild(secretKeyElement);

			// Generate XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File outputFile = new File("output.xml");
			StreamResult result = new StreamResult(outputFile);
			transformer.transform(source, result);

			System.out.println("XML file generated successfully.");

		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}
}
