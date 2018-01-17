package libraryt01;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
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
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String author) {
        this.authors = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdiction() {
        return ediction;
    }

    public void setEdiction(String ediction) {
        this.ediction = ediction;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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
