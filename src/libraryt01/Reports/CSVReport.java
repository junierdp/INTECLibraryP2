/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryt01.Reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import libraryt01.Book;
import libraryt01.Catalog;
import libraryt01.LibraryController;

/**
 *
 * @author JunierDP
 */
public class CSVReport {
    
    private final File dir = new File(".");
    private String route = "";
    
    public CSVReport() {
        try {
            this.route = dir.getCanonicalPath() + "/src/libraryt01/Reports/report.csv";
            File csvFile = new File(this.route);
            
            if (!csvFile.exists()) {
                csvFile.createNewFile();
            } else {
                csvFile.delete();
                csvFile.createNewFile();
            }
            
        } catch(IOException e) {
            
        }
    }
    
    public void generateCSVReport(List<Catalog> catalogs) {
        try {
            FileWriter csvFileWriter = new FileWriter(new File(this.route));
            BufferedWriter csvBufferedWriter = new BufferedWriter(csvFileWriter);
            
            csvBufferedWriter.write("CatalogoID,CatalogoNombre,LibroID,Autores,Nombre,Ediccion,ISBN,Fecha,Ciudad");
            csvBufferedWriter.newLine();
            for (Catalog catalog: catalogs) {
                for (Book book: catalog.getBooks()) {
                    csvBufferedWriter.write(catalog.getId() + "," + catalog.getName() + "," 
                    + book.getId() + "," + book.getAuthors() + "," + book.getName() + "," 
                    + book.getEdiction() + "," + book.getIsbn() + "," + book.getDate() + "," 
                    + book.getCity() + ",");
                    csvBufferedWriter.newLine();
                }
            }
            csvBufferedWriter.flush();
            csvBufferedWriter.close();
            
            LibraryController.get.print("CSV generado exitosamente.");
        } catch (IOException e) {
            LibraryController.get.print("Error generando CSV.");
        }
        
    }
}
