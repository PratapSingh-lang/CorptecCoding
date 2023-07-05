package com.corptec.etl.dataManipulation.helper;

import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.*;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
//import javax.xml.crypto.dsig.spec.XMLSignatureFactoryParameterSpec;
import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.Collections;

import java.security.cert.X509Certificate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Service
public class SignedXMLGenerator {

	public void generateSignedXml() {
		 // Extract file metadata details
        final String bucketName = "cutomeretl";
        final String objectName = "customer_data.csv";
        final String accessKey = "AKIAQRMXFYTNLSW3K3FN";
        final  String secretKey = "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";

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

            // Create the XML Signature Factory
            XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");

            // Create the Reference to the XML document
            Reference reference = signatureFactory.newReference(
                    "",
                    signatureFactory.newDigestMethod(DigestMethod.SHA256, null),
                    Collections.singletonList(
                            signatureFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)
                    ),
                    null,
                    null
            );

            // Create the SignedInfo
            SignedInfo signedInfo = signatureFactory.newSignedInfo(
                    signatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                            (C14NMethodParameterSpec) null),
                    signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA256, null),
                    Collections.singletonList(reference)
            );

            // Load the private key for signing
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(SignedXMLGenerator.class.getResourceAsStream("keystore.p12"), "keystorepassword".toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("alias", new KeyStore.PasswordProtection("keypassword".toCharArray()));
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();

            // Create the KeyInfo containing the X509Data
            X509Certificate certificate = (X509Certificate) privateKeyEntry.getCertificate();
            KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
            X509Data x509Data = keyInfoFactory.newX509Data(Collections.singletonList(certificate));
            KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));

            // Create the XML Signature
            XMLSignature signature = signatureFactory.newXMLSignature(signedInfo, keyInfo);

            // Sign the XML document
            DOMSignContext signContext = new DOMSignContext(privateKey, doc.getDocumentElement());
            signature.sign(signContext);

            // Output the signed XML to a file
            StreamResult result = new StreamResult("signed.xml");
            transformer.transform(source, result);

            System.out.println("Signed XML file created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
