package com.test.pdf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;

import com.test.pdf.rework.SigUtils;

public class PDFTest {

	public static void main(String[] args) {
		try {
			PDDocument doc = new PDDocument();
			PDPage page = new PDPage();
			doc.addPage(page);

			PDSignature sign = new PDSignature();

			sign.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);

			// subfilter for basic and PAdES Part 2 signatures
			sign.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);

			sign.setName("Test Sign");
			sign.setLocation("India");
			sign.setReason("To do a POC");
			
			PDDocument newDoc = PDDocument.load(new FileInputStream("sample2.pdf"));
			int accessPermissions = SigUtils.getMDPPermission(doc);
			 if (newDoc.getVersion() >= 1.5f && accessPermissions == 0)
	            {
	                SigUtils.setMDPPermission(newDoc, sign, 2);
	            }
		//	newDoc.addSignature(sign);
			newDoc.saveIncremental(new FileOutputStream("sample3_signed.pdf"));
			newDoc.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

}
