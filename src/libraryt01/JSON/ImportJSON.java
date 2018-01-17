/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryt01.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import libraryt01.Book;
import libraryt01.Catalog;
import libraryt01.LibraryController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author JunierDP
 */
public class ImportJSON {
    
    private final File dir = new File(".");
    private String route = "";
    
    public ImportJSON() {
        try {
            this.route = dir.getCanonicalPath() + "/src/libraryt01/JSON/json.json";
        } catch (IOException ex) {
            LibraryController.get.print("Error cargando archivo JSON.");
        }
    }
    
    public void loadJSONData() {
        String jsonString;
        try {
            FileReader jsonFileReader = new FileReader(new File(this.route));
            BufferedReader jsonBufferedReader = new BufferedReader(jsonFileReader);
            
            jsonString = jsonBufferedReader.readLine();
            
            JSONParser json = new JSONParser();
            
            JSONObject catalogObject = (JSONObject) json.parse(jsonString);
            JSONArray catalogArray = (JSONArray) catalogObject.get("catalogos");
            
            for (int i = 0; i < catalogArray.size(); i ++) {
                JSONObject _catalog = (JSONObject) catalogArray.get(i);
                                
                JSONArray bookArray = (JSONArray) _catalog.get("libros");
                
                List<Book> books = new ArrayList<>();
                
                for (int j = 0; j < bookArray.size(); j ++) {
                    JSONObject _book = (JSONObject) bookArray.get(i);
                    
                    books.add(new Book(
                            _book.get("autor").toString(),
                            _book.get("nombre").toString(),
                            _book.get("ediccion").toString(),
                            _book.get("isbn").toString(),
                            _book.get("fecha").toString(),
                            _book.get("ciudad").toString()
                    ));
                }
                
                Catalog catalog = new Catalog(0, _catalog.get("nombre").toString());
                
                LibraryController.get.addCatalogAndBooks(catalog, books);
            }            
            
            LibraryController.get.print("Datos del JSON cargados exitosamente.");
            
        } catch (FileNotFoundException ex) {
            LibraryController.get.print("Error cargando los datos del JSON.");
        } catch (IOException ex) {
            LibraryController.get.print("Error cargando los datos del JSON.");
        } catch (ParseException ex) {
            LibraryController.get.print("Error cargando los datos del JSON.");
        }
    }
}
