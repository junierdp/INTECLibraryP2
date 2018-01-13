package libraryt01;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JunierDP
 */
public class Catalog {
    private int id;
    private String name;
    private List<Book> books = new ArrayList<>();

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

    public Catalog(int id, String name, List<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
 
}
