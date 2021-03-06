/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryt01.Reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.*;
import java.util.List;
import libraryt01.Book;
import libraryt01.Catalog;
import libraryt01.LibraryController;

/**
 *
 * @author JunierDP
 */
public class PDFReport {
    
    private final File dir = new File(".");
    private String route = "";
    
    public PDFReport() {
    
    }
    
    public void generateReport(List<Catalog> catalogs) {
        Document document = new Document();
        try {
            route = dir.getCanonicalPath() + "/src/libraryt01/Reports/report.pdf";
            
            FileOutputStream file = new FileOutputStream(route);
            
            PdfWriter.getInstance(document, file).setInitialLeading(20);
            
            document.open();
            
            document.add(new Paragraph("Libros registrados",
				FontFactory.getFont("arial",   // fuente
				22,                            // tamaño
				Font.BOLD)));             // color

            
            PdfPTable table = new PdfPTable(3);
            
            for (Catalog catalog : catalogs) {
                for (Book book: catalog.getBooks()) {
                    table.addCell(catalog.getName());
                    table.addCell(book.getName());
                    table.addCell(book.getAuthors());
                }
            }
            
            document.add(table);
            
            document.close();
            
            LibraryController.get.print("PDF generado exitosamente.");
            
        } catch (DocumentException | IOException e) {
            LibraryController.get.print("Error generando PDF.");
        }
    }
}
