/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryt01.XML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import libraryt01.Catalog;
import libraryt01.LibraryController;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author JunierDP
 */
public class ExportXML {
    
    private final File dir = new File(".");
    private String route = "";
    
    public ExportXML() {
        try {
            this.route = dir.getCanonicalPath() + "/src/libraryt01/XML/xml.xml";
            File xmlFile = new File(this.route);
            
            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
            } else {
                xmlFile.delete();
                xmlFile.createNewFile();
            }
            
        } catch(IOException e) {
            LibraryController.get.print("Error exportando XML.");
        }
    }
    
    public void saveXML() {
        try {
            JAXBContext context = JAXBContext.newInstance(Catalog.class);
            Marshaller marshaller = context.createMarshaller();
            
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(LibraryController.catalogs, new FileWriter(this.route));
            
            LibraryController.get.print("XML exportado exitosamente.");
            
        } catch (JAXBException | IOException ex) {
            LibraryController.get.print("Error exportando XML.");
        }
    }
}
