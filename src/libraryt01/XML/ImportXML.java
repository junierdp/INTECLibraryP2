/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryt01.XML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import libraryt01.Book;
import libraryt01.Catalog;
import libraryt01.LibraryController;

/**
 *
 * @author JunierDP
 */
public class ImportXML {
    
    private final File dir = new File(".");
    private String route = "";
    
    public ImportXML() {
        try {
            this.route = dir.getCanonicalPath() + "/src/libraryt01/XML/xml.xml";
        } catch (IOException ex) {
            LibraryController.get.print("Error cargando archivo XML.");
        }
    }
    
    public void loadXMLData() {
        
        try {
            JAXBContext context = JAXBContext.newInstance(Catalog.class);
            Unmarshaller unmarshaller = context.createUnmarshaller(); 
            Catalog catalog = (Catalog) unmarshaller.unmarshal(new File(this.route));
            
            for (Catalog _catalog : catalog.getCatalogs()) { 
                LibraryController.get.addCatalogAndBooks(_catalog, _catalog.getBooks()); 
            }
            
            LibraryController.get.print("Datos del XML cargados exitosamente.");
        } catch (JAXBException ex) {
            LibraryController.get.print("Error cargando los datos del JSON.");
        }
        
    }
}
