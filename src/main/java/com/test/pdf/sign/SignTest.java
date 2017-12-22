package com.test.pdf.sign;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

public class SignTest {

	public static void main(String[] args) throws KeyStoreException, CertificateException, IOException,
			NoSuchAlgorithmException, UnrecoverableKeyException {
		// keytool -storepass 123456 -storetype PKCS12 -keystore file.p12
		// -genkey -alias client -keyalg RSA
		char[] password = "Intu1234".toCharArray();
		String fileIn = "sample_in.pdf";
		// load the keystore
		String cert = "/Users/cm1/MyProjects/pdfbox/certificates/final_result.pfx";

		String image_file = "alphabet-sign.jpeg";

		File ksFile = new File(cert);
		KeyStore keystore = KeyStore.getInstance("PKCS12");
		keystore.load(new FileInputStream(ksFile), password);

		File documentFile = new File(fileIn);

		CreateVisibleSignature signing = new CreateVisibleSignature(keystore, password);
		
		File signedDocumentFile;
		int page;
		String run = "new4";
		ByteArrayOutputStream pdfos = null;
		String out = null;
		PDDocument pdf = null;
		COSDocument cosDoc = null;
		
		pdfos = new ByteArrayOutputStream();
		PDFParser parser = new PDFParser(new RandomAccessBuffer(new FileInputStream(fileIn)));
		parser.parse();
		cosDoc = parser.getDocument();
		PDDocument doc = new PDDocument(cosDoc);
		try (FileInputStream imageStream = new FileInputStream(image_file)) {
			String name = documentFile.getName();
			String substring = name.substring(0, name.lastIndexOf('.'));
			signedDocumentFile = new File(documentFile.getParent(), substring + "_signed_"+run+".pdf");
			// page is 1-based here
			page = doc.getPages().getCount();
			signing.setVisibleSignDesigner(fileIn, 350, 500, -50, imageStream, page);
		}
		signing.setVisibleSignatureProperties("Name", "Location", "To Test Certificate", 0, page, true);
		InputStream is = new FileInputStream(documentFile);
		signing.signPDF(is, signedDocumentFile, "Signing here");
	//	signing.signPDF(doc, "Signing here");
	}
}
