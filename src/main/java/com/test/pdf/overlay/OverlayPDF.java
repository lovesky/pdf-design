package com.test.pdf.overlay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.multipdf.Overlay.Position;
import org.apache.pdfbox.pdmodel.PDDocument;
 
/**
 * 
 * Adds an overlay to an existing PDF document.
 *  
 * Based on code contributed by Balazs Jerk. 
 * 
 */
public final class OverlayPDF 
{
    private static final Log LOG = LogFactory.getLog(OverlayPDF.class);
 
    // Command line options
    private static final String POSITION = "-position";
    private static final String ODD = "-odd";
    private static final String EVEN = "-even";
    private static final String FIRST = "-first";
    private static final String LAST = "-last";
    private static final String PAGE = "-page";
    private static final String USEALLPAGES = "-useAllPages";
 
    private OverlayPDF()
    {
    }    
     
    /**
     * This will overlay a document and write out the results.
     *
     * @param args command line arguments
     * @throws IOException if something went wrong
     */
    public static void main(final String[] args) throws IOException
    {
        // suppress the Dock icon on OS X
        System.setProperty("apple.awt.UIElement", "true");
 
        String outputFilename = null;
        Overlay overlayer = new Overlay();
        Map<Integer, String> specificPageOverlayFile = new HashMap<Integer, String>();
        // input arguments
                overlayer.setInputFile("");
                outputFilename = "";
                    overlayer.setOverlayPosition(Position.FOREGROUND);
               //     overlayer.setOverlayPosition(Position.BACKGROUND);

           //     overlayer.setOddPageOverlayFile(args[i + 1].trim());
            //    overlayer.setEvenPageOverlayFile(args[i + 1].trim());
           //     overlayer.setFirstPageOverlayFile(args[i + 1].trim());
               // overlayer.setLastPageOverlayFile(args[i + 1].trim());
         //       overlayer.setAllPagesOverlayFile(args[i + 1].trim());
      //          specificPageOverlayFile.put(Integer.parseInt(args[i + 1].trim()), args[i + 2].trim());
      //          overlayer.setDefaultOverlayFile(arg);
      /*   
        if (overlayer.getInputFile() == null || outputFilename == null) 
        {
            usage();
        }
         */
        try
        {
            PDDocument result = overlayer.overlay(specificPageOverlayFile);
            result.save(outputFilename);
            result.close();
            // close the input files AFTER saving the resulting file as some 
            // streams are shared among the input and the output files
            overlayer.close();
        } 
        catch (IOException e) 
        {
            LOG.error("Overlay failed: " + e.getMessage(), e);
            throw e;
        }
    }
 
    private static void usage()
    {
        String message = "Usage: java -jar pdfbox-app-x.y.z.jar OverlayPDF <inputfile> [options] <outputfile>\n"
                + "\nOptions:\n"
                + "  <inputfile>                                  : input file\n"
                + "  <defaultOverlay.pdf>                         : default overlay file\n"
                + "  -odd <oddPageOverlay.pdf>                    : overlay file used for odd pages\n"
                + "  -even <evenPageOverlay.pdf>                  : overlay file used for even pages\n"
                + "  -first <firstPageOverlay.pdf>                : overlay file used for the first page\n"
                + "  -last <lastPageOverlay.pdf>                  : overlay file used for the last page\n"
                + "  -useAllPages <allPagesOverlay.pdf>           : overlay file used for overlay, all pages"
                + " are used by simply repeating them\n"
                + "  -page <pageNumber> <specificPageOverlay.pdf> : overlay file used for "
                + "the given page number, may occur more than once\n"
                + "  -position foreground|background              : where to put the overlay "
                + "file: foreground or background\n"
                + "  <outputfile>                                 : output file";
 
        System.err.println(message);
        System.exit( 1 );
    }
 
}