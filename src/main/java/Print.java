/*
 *Bill Receipt Printer Application
 * v 1.0
 * Developed by Aman Nirala
 * Email - amansofttechinfo@gmail.com
 * Website - http://www.amansofttechinfo.weebly.com
 * COmpany - AmanSoft Developers
 */

import java.awt.*;
import java.awt.print.*;




class print implements Printable {
    public static void main(String[] args) {
        new print();
    }

    public print() {
        PrinterJob printjob = PrinterJob.getPrinterJob();
        printjob.setPrintable(this, getPageFormat(printjob));
        if (printjob.printDialog()) {
            try {
                printjob.print();
            } catch (Exception ex) {
                System.out.println("ERROR : " + ex);
            }
        }
    }

    /*CODE TO ALIGN TEXT
         * ----------------------
         * ---------------------------------------
         * --------------------------------------------------------
         * --------------------------------------------------------------------------
         * ------------------------------------------------------------------------------------------------
         * ----------------------------------------------------------------------------------------------------------------------------
         * ----------------------------------------------------------------------------------------------------------------------------------------------
         * ---------------------------------------------------------------------------------------------------------------------------------------------------
         * ---------------------------------------------------------------------------------------------------------------------------------------------------------
         * --------------------------------------------------------------------------------------------------------------------------------------------------------
         ----------------------------------------------------------------------------------------------------------------------------------------------------------*/
    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double middleHeight = 8.0;
        double headerHeight = 2.0;
        double footerHeight = 2.0;
        double width = convert_CM_To_PPI(8); //printer know only point per inch.default value is 72ppi
        double height = convert_CM_To_PPI(headerHeight + middleHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - convert_CM_To_PPI(1)); //define boarder size    after that print area width is about 180 points

        pf.setOrientation(PageFormat.PORTRAIT); //select orientation portrait or landscape but for this time portrait
        pf.setPaper(paper);

        return pf;
    }

    protected static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    public int print(Graphics g, PageFormat pageFormat, int page) {
        if (page == 0) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(
                    (int) pageFormat.getImageableX(),
                    (int) pageFormat.getImageableY()
            );

            try {
                /*Draw Header*/
                int y = 20;
                int yShift = 10;
                int headerRectHeight = 15;
                int headerRectHeighta = 40;
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                //========================================================================================
                //                      ENTER THE TEXT AREA TO PRINT
                //===============================================================================================

                g2d.drawString("-------------------------------------", 12, y);
                y += yShift; //Line Breaker to the scale | Copy this statement and paste to enter Line Breaker
                g2d.drawString("      <Message>       ", 12, y);
                y += yShift; //Enter your message
                g2d.drawString("-------------------------------------", 12, 15);
                y += headerRectHeight; // Line breaker END to Scle
                /*Go on entering in the following formate for a new line
                 *
                 * Formate :   g2d.drawString("    YOUR MESSAGE  ",12,y);y+=yShift;
                 */

                return (PAGE_EXISTS);
            } catch (Exception ex) {
                System.out.println("ERROR : " + ex);
            }
            return (PAGE_EXISTS);
        } else return (NO_SUCH_PAGE);
    }
}
