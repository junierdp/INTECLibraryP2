package libraryt01;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

/**
 *
 * @author JunierDP
 * - ID
 * - Autor (es)
 * - Nombre
 * - Edicion
 * - ISBN
 * - Fecha
 * - Ciudad
 */

@XmlRootElement(name="libro")
@XmlType(propOrder={"authors", "name", "ediction", "isbn", "date", "city"})
public class Book implements JSONStreamAware {
    
    private int id;
    private String authors;
    private String name;
    private String ediction;
    private String isbn;
    private String date;
    private String city;
    private int catalogId;
    
    public Book(){}
    
    public Book(String authors, String name, String ediction, String isbn, String date, String city) {
        super();
        this.authors = authors;
        this.name = name;
        this.ediction = ediction;
        this.isbn = isbn;
        this.date = date;
        this.city = city;
    }

    public Book(int id, String authors, String name, String ediction, String isbn, String date, String city) {
        super();
        this.id = id;
        this.authors = authors;
        this.name = name;
        this.ediction = ediction;
        this.isbn = isbn;
        this.date = date;
        this.city = city;
    }
    
    public Book(int id, String authors, String name, String ediction, String isbn, String date, String city, int catalogId) {
        super();
        this.id = id;
        this.authors = authors;
        this.name = name;
        this.ediction = ediction;
        this.isbn = isbn;
        this.date = date;
        this.city = city;
        this.catalogId = catalogId;
    }
    
    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name="autor")
    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String author) {
        this.authors = author;
    }

    @XmlElement(name="nombre")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="edicion")
    public String getEdiction() {
        return ediction;
    }

    public void setEdiction(String ediction) {
        this.ediction = ediction;
    }

    @XmlElement(name="isbn")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @XmlElement(name="fecha")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name="ciudad")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @XmlTransient
    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    @Override
    public void writeJSONString(Writer out) throws IOException {
        LinkedHashMap obj = new LinkedHashMap();
        obj.put("autor", this.authors);
        obj.put("nombre", this.name);
        obj.put("ediccion", this.ediction);
        obj.put("isbn", this.isbn);
        obj.put("fecha", this.date);
        obj.put("ciudad", this.city);
        JSONValue.writeJSONString(obj, out);
    }
}
