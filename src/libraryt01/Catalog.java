package libraryt01;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

/**
 *
 * @author JunierDP
 */

@XmlType(propOrder={"catalogs","name", "books"})
@XmlRootElement(name="catalogos")
public class Catalog implements JSONStreamAware {
    private int id;
    private String name;
    private ArrayList<Book> books = new ArrayList<>();
    
    private ArrayList<Catalog> catalogs = null;

    public Catalog() {
    }
    
    public Catalog(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Catalog(Catalog ctg) {
        this.id = ctg.getId();
        this.name = ctg.getName();
        this.books = ctg.getBooks();
    }

    public Catalog(int id, String name, ArrayList<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }
    
    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name="nombre")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name="libros")
    @XmlElement(name="libro")
    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @XmlElement(name="catalogo")
    public ArrayList<Catalog> getCatalogs() {
        return this.catalogs;
    }

    public void setCatalogs(ArrayList<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    @Override
    public void writeJSONString(Writer out) throws IOException {
        LinkedHashMap obj = new LinkedHashMap();
        obj.put("nombre", this.name);
        obj.put("libros", this.books);
        JSONValue.writeJSONString(obj, out);
    }
 
}
