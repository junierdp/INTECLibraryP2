/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryt01.JSON;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import libraryt01.Catalog;
import libraryt01.LibraryController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author JunierDP
 */
public class ExportJSON {
    
    final private List<Catalog> catalog;
    private final File dir = new File(".");
    private String route = "";
    
    public ExportJSON(List<Catalog> catalog) {
        this.catalog = catalog;
        try {
            this.route = dir.getCanonicalPath() + "/src/libraryt01/JSON/json.json";
            File jsonFile = new File(this.route);
            
            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
            } else {
                jsonFile.delete();
                jsonFile.createNewFile();
            }
            
        } catch(IOException e) {
            LibraryController.get.print("Error exportando JSON.");
        }
    }
    
    public void saveJSON() {
        JSONObject obj = new JSONObject();
        JSONArray catalogArray = new JSONArray();
        
        this.catalog.forEach((catalogData) -> {
            catalogArray.add(catalogData);
        });
        
        StringWriter out = new StringWriter();
        try {
            obj.put("catalogos", catalogArray);
            obj.writeJSONString(out);

            FileWriter jsonFileWriter = new FileWriter(new File(this.route));
            BufferedWriter jsonBufferedWriter = new BufferedWriter(jsonFileWriter);
            
            jsonBufferedWriter.write(out.toString());
            
            jsonBufferedWriter.flush();
            jsonBufferedWriter.close();
            
            LibraryController.get.print("JSON exportado exitosamente.");
        } catch (IOException e) {
            LibraryController.get.print("Error exportando JSON.");
        }
    }
}
