package com.test.pdf.overlay;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;

public class Test {

	public static void main(String[] args) throws IOException {
		  File file2 = new File("/Users/cm1/webs_codebase/webs-pri-document-generation-api/pdf-sample2.pdf");
	        PDDocument overlayDoc = PDDocument.load(file2);
	        Overlay overlayObj = new Overlay();

	        PDDocument originalDoc = PDDocument.load(new File("/Users/cm1/webs_codebase/webs-pri-document-generation-api/SAMPLE.pdf"));
	        overlayObj.setOverlayPosition(Overlay.Position.FOREGROUND);
	        overlayObj.setInputPDF(originalDoc);
	        overlayObj.setAllPagesOverlayPDF(overlayDoc);  
	        
	        //alternatives?
	        Map<Integer, String> ovmap = new HashMap<Integer, String>();            
	        overlayObj.overlay(ovmap);
	        originalDoc.save("Path.pdf");

	        overlayDoc.close();
	        originalDoc.close();
	}

}
