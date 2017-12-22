package com.test.pdf.finall;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class SignTest {

	public static void main(String[] args) throws KeyStoreException, CertificateException, IOException,
			NoSuchAlgorithmException, UnrecoverableKeyException {
		// keytool -storepass 123456 -storetype PKCS12 -keystore file.p12
		// -genkey -alias client -keyalg RSA
		String tsaUrl = "http://timestamp.globalsign.com/scripts/timstamp.dll";
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
		int run = 18;
		try (FileInputStream imageStream = new FileInputStream(image_file)) {
			String name = documentFile.getName();
			String substring = name.substring(0, name.lastIndexOf('.'));
			signedDocumentFile = new File(documentFile.getParent(), substring + "_signed_"+run+".pdf");
			// page is 1-based here
			page = 1;
	//		signing.setVisibleSignDesigner(fileIn, 350, 500, 0, imageStream, page);
		}
		signing.setVisibleSignatureProperties("Name", "Location", "To Test Certificate", 0, page, true);
		InputStream is = new FileInputStream(documentFile);
		signing.signPDF(is, signedDocumentFile, null, "Signing here");
	}
}
