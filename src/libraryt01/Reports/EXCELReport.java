/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryt01.Reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import libraryt01.Book;
import libraryt01.Catalog;
import libraryt01.LibraryController;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author JunierDP
 */
public class EXCELReport {
    
    private final File dir = new File(".");
    private String route = "";
    
    public EXCELReport() {
        try {
            this.route = dir.getCanonicalPath() + "/src/libraryt01/Reports/report.xls";
            File excelFile = new File(this.route);
            
            if (!excelFile.exists()) {
                excelFile.createNewFile();
            } else {
                excelFile.delete();
                excelFile.createNewFile();
            }
            
        } catch(IOException e) {
            
        }
    }
    
    public void generateReport(List<Catalog> catalogs) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            FileOutputStream file = new FileOutputStream(this.route);
            HSSFSheet sheet = workbook.createSheet("Libros");
            
            String[] headers = new String[] {
              "CatalogoID", "Catalogo", "LibroID", "Nombre", "Autor", "Ediccion",
              "ISBN", "Fecha", "Ciudad"
            };
            
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            
            HSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i ++) {
                HSSFCell cell = headerRow.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers[i]);
            }
            
            int row = 1;
            for (Catalog catalog : catalogs) {
                for (Book book: catalog.getBooks()) {
                    HSSFRow rowData = sheet.createRow(row);
                    
                    rowData.createCell(0).setCellValue(catalog.getId());
                    rowData.createCell(1).setCellValue(catalog.getName());
                    rowData.createCell(2).setCellValue(book.getId());
                    rowData.createCell(3).setCellValue(book.getName());
                    rowData.createCell(4).setCellValue(book.getAuthors());
                    rowData.createCell(5).setCellValue(book.getEdiction());
                    rowData.createCell(6).setCellValue(book.getIsbn());
                    rowData.createCell(7).setCellValue(book.getDate());
                    rowData.createCell(8).setCellValue(book.getCity());
                    row ++;
                }
            }
            
            workbook.write(file);
            file.close();
            
            LibraryController.get.print("EXCEL generado exitosamente.");
        } catch(IOException e) {
            LibraryController.get.print("Error generando EXCEL.");
        }
    }
}
