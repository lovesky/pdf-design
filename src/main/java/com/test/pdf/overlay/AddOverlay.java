package com.test.pdf.overlay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;

public class AddOverlay {

	public void addOverlay() throws IOException {
		PDFont font = PDType1Font.COURIER_OBLIQUE;
		PDDocument originalDoc = PDDocument.load(new File("/Users/cm1/webs_codebase/webs-pri-document-generation-api/pdf-sample2.pdf"));
		
		PDPage page1 = originalDoc.getPage(0);
		PDPageContentStream contentStream = new PDPageContentStream(originalDoc, page1, AppendMode.PREPEND, true, true);
		contentStream.setFont(font, 50);
		contentStream.setNonStrokingColor(0);
		contentStream.beginText();
		contentStream.showText("deprecated"); // deprecated. Use
		
		contentStream.endText();
		contentStream.close();
		originalDoc.save("/Users/cm1/webs_codebase/webs-pri-document-generation-api/pdf-sample3_out.pdf");
		originalDoc.close();
	    
	}

	public static void main(String... args) throws IOException {
	//	new AddOverlay().addOverlay();
	//	watermarkPDF(new File("/Users/cm1/webs_codebase/webs-pri-document-generation-api/pdf-sample2.pdf"));
		watermarkPDF(new File("epf-Joint Declaration-form.pdf"), "eagle.jpeg");
	}
	
	
	public static void watermarkPDF (File fileStored) throws IOException {
	    PDDocument doc;
	    doc = PDDocument.load(fileStored);
	    String ts = "Some sample text";
	    for(PDPage page:doc.getPages()){
	        PDPageContentStream cs = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true);
	       
	        PDFont font = PDType1Font.HELVETICA_BOLD;
	        float fontSize = 14.0f;
	        PDResources resources = page.getResources();
	        PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();
	        r0.setNonStrokingAlphaConstant(0.5f);
	        cs.setGraphicsStateParameters(r0);
	        cs.setNonStrokingColor(40,35,30);//Red
	        cs.beginText();
	        cs.setFont(font, fontSize);
	        cs.setTextMatrix(Matrix.getRotateInstance(145, 250, 550));
	        cs.showText(ts);
	        cs.endText();
	        cs.close();
	        
	    }
	   doc.save(fileStored);
}
	

	public static void watermarkPDF (File fileStored, String image) throws IOException {
	    PDDocument doc;
	    
	    doc = PDDocument.load(fileStored);
	    PDImageXObject pdImage = PDImageXObject.createFromFile(image, doc);
	    String ts = "Some sample text";
	    for(PDPage page:doc.getPages()){
	        PDPageContentStream cs = new PDPageContentStream(doc, page, AppendMode.PREPEND, true, true);
	       
	        PDFont font = PDType1Font.HELVETICA_BOLD;
	        float fontSize = 14.0f;
	        PDResources resources = page.getResources();
	        PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();
	        r0.setNonStrokingAlphaConstant(0.5f);
	//        cs.setGraphicsStateParameters(r0);
	 //       cs.setNonStrokingColor(40,35,30);//Red
	   //     cs.beginText();
	        cs.drawImage(pdImage, 250, 550);
	  //      cs.setFont(font, fontSize);
	    //    cs.setTextMatrix(Matrix.getRotateInstance(145, 250, 550));
	   //     cs.showText(ts);
	  //      cs.endText();
	        cs.close();
	 
	    }
	   doc.save(fileStored);
}
}
