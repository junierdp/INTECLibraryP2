package libraryt01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

/**
 *
 * @author JunierDP
 */
public class LibraryController {
    
    public static List<Catalog> catalogs = new ArrayList<>();
    public static LibraryController get = new LibraryController();
    private String catalogRoute = "";
    private String bookRoute = "";
        
    public LibraryController() {
        File mydir = new File(".");
        try{
            this.catalogRoute = mydir.getCanonicalPath() + "/src/libraryt01/catalogs.txt"; 
            File catalogFile = new File(this.catalogRoute);
            if (!catalogFile.exists()) {
                catalogFile.createNewFile();
            }
            this.bookRoute = mydir.getCanonicalPath() + "/src/libraryt01/books.txt";
            File bookFile = new File(this.bookRoute);
            if (!bookFile.exists()) {
                bookFile.createNewFile();
            }
            
            this.loadSavedData();
        }catch(Exception e){
            System.out.println("Error cargando archivos." + e.toString());
        }
    }

    Scanner sc = new Scanner(System.in);
    
    public void loadSavedData() throws FileNotFoundException, IOException {
        List<Book> books = new ArrayList<>();
        try {
            FileReader bookFileReader = new FileReader(new File(this.bookRoute));
            BufferedReader bookBufferedReader = new BufferedReader(bookFileReader);
            
            String bookLine = null;
            while ((bookLine = bookBufferedReader.readLine()) != null) {
                String[] bookData = bookLine.split("\\|");
                Book book = new Book(Integer.parseInt(bookData[0]), bookData[1], bookData[2], bookData[3], bookData[4], bookData[5], bookData[6], Integer.parseInt(bookData[7]));
                books.add(book);
            }
            
            FileReader catalogFileReader = new FileReader(new File(this.catalogRoute));
            BufferedReader catalogBufferedReader = new BufferedReader(catalogFileReader);
            
            String catalogLine = null;
            while ((catalogLine = catalogBufferedReader.readLine()) != null) {
                String[] catalogData = catalogLine.split("\\|");
                Catalog catalog = new Catalog(Integer.parseInt(catalogData[0]), catalogData[1]);
                
                for (Book book : books) {
                    if (book.getCatalogId() == catalog.getId()) {
                        catalog.getBooks().add(book);
                    }
                }
                
                LibraryController.catalogs.add(catalog);
            }
            
            bookBufferedReader.close();
            catalogBufferedReader.close();
            bookFileReader.close();
            catalogFileReader.close();
            
        } catch (FileNotFoundException ex) {
            System.out.print("Error" + ex.toString());
        } catch (IOException ex) {
            System.out.print("Error" + ex.toString());
        }
    }
    
    public void saveData() {
        try {
            FileWriter catalogFileWriter = new FileWriter(new File(this.catalogRoute));
            BufferedWriter catalogBufferedWriter = new BufferedWriter(catalogFileWriter);
            
            FileWriter bookFileWriter = new FileWriter(new File(this.bookRoute));
            BufferedWriter bookBufferedWriter = new BufferedWriter(bookFileWriter);
            
            for (Catalog catalog : LibraryController.catalogs) {
                catalogBufferedWriter.write(catalog.getId() + "|" + catalog.getName());
                catalogBufferedWriter.newLine();
                
                for (Book book : catalog.getBooks()) {
                    bookBufferedWriter.write(book.getId() + "|" + book.getAuthors() + "|" + book.getName() + "|" + book.getEdiction() + "|" + book.getIsbn() + "|" + book.getDate() + "|" + book.getCity() + "|" + catalog.getId());
                    bookBufferedWriter.newLine();
                }
            }
            
            catalogBufferedWriter.flush();
            catalogBufferedWriter.close();
            bookBufferedWriter.flush();
            bookBufferedWriter.close();
        } catch (IOException ex) {
            System.out.print("Error" + ex.toString());
        }
        
    }
    
    public void print(String text){
        System.out.println("\n=================================================");
        System.out.println(text);
        System.out.println("=================================================\n");
    }
    
    public void addCatalog() {
        Catalog catalog = new Catalog();
        
        this.print("Agregar nuevo catalogo");
        
        catalog.setName(this.returnStringValue("Nombre del catalogo: "));
        if (LibraryController.catalogs.size() > 0){
            catalog.setId(LibraryController.catalogs.get(LibraryController.catalogs.size() - 1).getId() + 1);
        } else {
            catalog.setId(1);
        }
        
        LibraryController.catalogs.add(catalog);        
    }
    
    public void showCatalogs() {
        if (LibraryController.catalogs.isEmpty()) {
            this.print("No hay Catalogos registrados");
        } else {
            this.print("Libros registrados");

            int idSize = 0;
            int nameSize = 0;
            String idHeader = "ID";
            String nameHeader = "Nombre";

            for (Catalog catalog: LibraryController.catalogs) {
                if (String.valueOf(catalog.getId()).length() > idSize) {
                    idSize = String.valueOf(catalog.getId()).length();
                }
                if (catalog.getName().length() > nameSize) {
                    nameSize = catalog.getName().length();
                }
            }

            for (int i = 0; i < idSize; i ++) {
                idHeader += " ";
            }
            for (int i = 0; i < nameSize; i ++) {
                nameHeader += " ";
            }

            String headerLine = "= " + idHeader + " = " + nameHeader + " =";
            String line = "";
            for (int i = 0; i < headerLine.length(); i ++) {
              line += "=";
            }

            System.out.println(line);
            System.out.println(headerLine);
            System.out.println(line);

            for (Catalog catalog : LibraryController.catalogs) {
                String id = String.valueOf(catalog.getId());
                String name = catalog.getName();

                int idLenght = id.length();
                int nameLenght = name.length();

                while(idLenght < idHeader.length()){ 
                    id += " ";
                    idLenght ++;
                }

                while(nameLenght < nameHeader.length()) {
                    name += " ";
                    nameLenght ++;
                }

                String row = "= " + id + " = " + name + " =";

                System.out.println(row);
            }
            System.out.println(line);
        }
    }
    
    public void editCatalog() {
        this.print("Editar catalogo");
        
        int id = returnIntValue("Inserte el ID del catalogo que desea editar: ");
        
        Catalog catalogFounded = null;
        for (Catalog catalog : LibraryController.catalogs) {
            if (catalog.getId() == id) {
                catalogFounded = catalog;
                break;
            }
        }
        
        int option = 0;
        
        if (catalogFounded != null) {
            this.print("Información del catalogo");
            
            System.out.println("1. Editar nombre: " + catalogFounded.getName());
            System.out.println("2. Terminar de editar.");
            
            option = returnIntValue("Elija una opcion: ");
            
            if (option > 0 && option < 3) {
                this.editCatalogAction(option, catalogFounded.getId(), catalogFounded);
            }
        } else {
            this.print("No se ha encontrado el libro que desea editar.");
        }
    }
    
    public void editCatalogAction(int option, int catalogId, Catalog catalog) {
        String data;
        switch (option) {
            case 1:
                data = this.returnStringValue("Introduzca el nuevo nombre: ");
                int indexCatalog = LibraryController.catalogs.indexOf(catalog);
                LibraryController.catalogs.get(indexCatalog).setName(data);
                break;
            case 2: 
                this.print("Edicion finalizada.");
                break;
        }
    }
    
    public void manageCatalog() {
        this.print("Administrar catalogo");
        
        int id = returnIntValue("Inserte el id del catalogo que desea administrar: ");
        
        for (Catalog catalog : LibraryController.catalogs) {
            if (catalog.getId() == id) {
                this.showBookMenu(catalog);
                return;
            }
        }
        this.print("El catalogo insertado no existe");
    }
    
    public void deleteCatalog() {
        this.print("Eliminar catalogo");
        
        int id = returnIntValue("Inserte el id del catalogo que desea eliminar: ");
        
        Catalog mainCatalog = null;
            
        for (Catalog catalog : LibraryController.catalogs) {
            if (catalog.getId() == id) {
                mainCatalog = catalog;
                //LibraryController.catalogs.remove(catalog);
                //System.out.print("El libro se ha eliminado");
                //return;
            }
        }
        
        if (mainCatalog != null) {
            int option = 0;
            this.print("Que desea hacer con los libros de catalogo #" + mainCatalog.getId());
            System.out.println("1. Eliminar libros.");
            System.out.println("2. Trasladar libros.");
                        
            option = this.returnIntValue("Seleccione una opcion del menu: ");
            this.deleteCatalogActions(option, mainCatalog);
        } else {
            this.print("El catalogo insertado no existe");
        }
    }
    
    public void deleteCatalogActions(int option, Catalog catalog) {
        switch (option) {
            case 1:
                LibraryController.catalogs.remove(catalog);
                this.print("Catalogo y libros eliminados.");
                break;
            case 2:
                int id = this.returnIntValue("Inserte el id del catalogo: ");
                this.trasladarCatalog(id, catalog);
                LibraryController.catalogs.remove(catalog);
                break;
        }
    }
    
    public void trasladarCatalog(int id, Catalog catalog) {
        for (Catalog catalogData : LibraryController.catalogs) {
            if (catalogData.getId() == id) {
                for (Book book : catalog.getBooks()) {
                    book.setCatalogId(id);
                    catalogData.getBooks().add(book);
                }
                return;
            }
        }
    }
    
    public void showBookMenu(Catalog catalog) {
        int option = 0;
        do {
            this.print("============ MENU ============");
            System.out.println("1. Agregar libro");
            System.out.println("2. Editar libro");
            System.out.println("3. Borrar libro");
            System.out.println("4. Listar todos los libros");
            System.out.println("5. Buscar libro por ID");
            System.out.println("6. Buscar libro por nombre");
            System.out.println("7. Buscar libro por ISBN");
            System.out.println("8. Salir");
                        
            option = this.returnIntValue("Seleccione una opcion del menu: ");
            
            if (this.validateOptions(option,1,8)) {
                this.actions(option, catalog);
            }            
                
        } while (option != 8);
    }
    
    public void actions(int option, Catalog catalog) {
        switch (option) {
            case 1: 
                LibraryController.get.addBook(catalog);
                this.saveData();
                break;
            case 2:
                LibraryController.get.editBook(catalog);
                this.saveData();
                break;
            case 3:
                LibraryController.get.deleteBook(catalog);
                this.saveData();
                break;
            case 4: 
                LibraryController.get.showAllBooks(catalog);
                break;
            case 5:
                LibraryController.get.searchById(catalog);
                break;
            case 6:
                LibraryController.get.searchProductByName(catalog);
                break;
            case 7:
                LibraryController.get.searchProductByISBN(catalog);
                break;
            case 8:
                this.print("El programa se ha cerrado.");
                break;
        }
    }
    
    public int returnIntValue(String message) {
        System.out.println(message);
        
        int intValue = 0;
        while (intValue == 0) {
            try {
                String value = sc.nextLine();
                intValue = Integer.valueOf(value);
            } catch (Exception e) {
                System.out.println("El valor tiene que ser un numero.");
                System.out.println(message);
            }
        }
        return intValue;
    }
    
    public String returnStringValue(String message) {
        System.out.println(message);
        
        String str;
        str = sc.nextLine();
        
        while(str.isEmpty()){
            
            System.out.println("El valor insertado no puede estar vacio.");
            System.out.println(message);
            
            str = sc.nextLine();
        }
        
        return str;
    }
    
    public void addBook(Catalog catalog) {
        Book book = new Book();
        
        int indexCatalog = LibraryController.catalogs.indexOf(catalog);
        int index = LibraryController.catalogs.get(indexCatalog).getBooks().size() + 1;
        
        this.print("Agregar nuevo libro");

        book.setName(this.returnStringValue("Nombre del libro: "));
        
        book.setAuthors(this.returnStringValue("Autor del libro: "));
        
        book.setEdiction(this.returnStringValue("Edicion del libro: "));
        
        book.setIsbn(this.returnStringValue("ISBN del libro: "));
        
        book.setDate(this.returnStringValue("Fecha del libro: "));
        
        book.setCity(this.returnStringValue("Ciudad del libro: "));
        
        book.setId(index);
        
        LibraryController.catalogs.get(catalog.getId() - 1).getBooks().add(book);
        
        this.print("Libro insertado, ID: " + (index));
    }
    
    public void editBook(Catalog catalog) {
        int id;
        int option = 0;
        this.print("Editar libro");
        
        id = returnIntValue("Seleccione el libro que desea editar: ");
        
        Book book;
        
        try {
            int indexCatalog = LibraryController.catalogs.indexOf(catalog);
            book = LibraryController.catalogs.get(indexCatalog).getBooks().get(id - 1);
        } catch (Exception e) {
            book = null;
        }
                
        if (book != null) {
            do{
                System.out.println("==========================");
                System.out.println("Informacion del libro " + id);
                System.out.println("==========================");
                System.out.println("1. Editar nombre: " + book.getName());
                System.out.println("2. Editar autor: " + book.getAuthors());
                System.out.println("3. Editar edicion: " + book.getEdiction());
                System.out.println("4. Editar ISBN: " + book.getIsbn());
                System.out.println("5. Editar fecha: " + book.getDate());
                System.out.println("6. Editar ciudad: " + book.getCity());
                System.out.println("7. Terminar de editar.");
                
                option = returnIntValue("Elija una opcion: ");
                
                if (this.validateOptions(option, 1,7)) {
                    this.editOption(option, id - 1, catalog);
                }
            
            } while(option != 7);   
        } else {
            this.print("No se ha encontrado el libro seleccionado");
        }
    }
    
    public Boolean validateOptions(int option, int start, int end){
        return !(option < start  || option > end);
    }
    
    public void editOption(int option, int bookId, Catalog catalog) {
        String data;
        
        int indexCatalog = LibraryController.catalogs.indexOf(catalog);
        
        switch (option) {
            case 1:
                data = this.returnStringValue("Introduzca el nuevo nombre: ");
                LibraryController.catalogs.get(indexCatalog).getBooks().get(bookId).setName(data);
                break;
            case 2:
                data = this.returnStringValue("Introduzca el nuevo autor: ");
                LibraryController.catalogs.get(indexCatalog).getBooks().get(bookId).setAuthors(data);
                break;
            case 3:
                data = this.returnStringValue("Introduzca la nueva edicion: ");
                LibraryController.catalogs.get(indexCatalog).getBooks().get(bookId).setEdiction(data);
                break;
            case 4:
                data = this.returnStringValue("Introduzca el nuevo ISBN: ");
                LibraryController.catalogs.get(indexCatalog).getBooks().get(bookId).setIsbn(data);
                break;
            case 5:
                data = this.returnStringValue("Introduzca la nueva fecha: ");
                LibraryController.catalogs.get(indexCatalog).getBooks().get(bookId).setDate(data);
                break;
            case 6:
                data = this.returnStringValue("Introduzca la nueva ciudad: ");
                LibraryController.catalogs.get(indexCatalog).getBooks().get(bookId).setCity(data);
                break;
            case 7:
                this.print("Edicion finalizada.");
                break;
        }
    }
    
    public void deleteBook(Catalog catalog) {
        this.print("Eliminar libro");
        
        int id = returnIntValue("Seleccione el libro que desea borrar: ");
        
        Book book;
        
        int indexCatalog = LibraryController.catalogs.indexOf(catalog);
        
        try {
            book = LibraryController.catalogs.get(indexCatalog).getBooks().get(id - 1);
        } catch (Exception e) {
            book = null;
        }
        
        if (book != null) {
            LibraryController.catalogs.get(indexCatalog).getBooks().remove(book);
            this.print("El libro se ha eliminado exitosamente.");
        } else {
            this.print("No se ha encontrado el libro seleccionado");
        }
    }
    
    public void showAllBooks(Catalog catalog) {
       this.print("Lista de libros");
              
       int idSize = 0;
       String idHeader = "ID";
       int nameSize = 0;
       String nameHeader = "Nombre";
       int authorSize = 0;
       String authorHeader = "Autor / es";
       int edictionSize = 0;
       String edictionHeader = "Ediccion";
       int isbnSize = 0;
       String isbnHeader = "ISBN";
       int dateSize = 0;
       String dateHeader = "Fehca";
       int citySize = 0;
       String cityHeader = "Ciudad";
       
       for (Book book : catalog.getBooks()) {
           if (String.valueOf(book.getId()).length() > idSize) {
               idSize = String.valueOf(book.getId()).length();
           }
           if (book.getName().length() > nameSize) {
               nameSize = book.getName().length();
           }
           if (book.getAuthors().length() > authorSize) {
               authorSize = book.getAuthors().length();
           }
           if (book.getEdiction().length() > edictionSize) {
               edictionSize = book.getEdiction().length();
           }
           if (book.getIsbn().length() > isbnSize) {
               isbnSize = book.getIsbn().length();
           }
           if (book.getDate().length() > dateSize) {
               dateSize = book.getDate().length();
           }
           if (book.getCity().length() > citySize) {
               citySize = book.getCity().length();
           }
       }
       
        for (int i = 0; i < idSize; i ++) {
            idHeader += " ";
        }
        for (int i = 0; i < nameSize; i ++) {
            nameHeader += " ";
        }
        for (int i = 0; i < authorSize; i ++) {
            authorHeader += " ";
        }
        for (int i = 0; i < edictionSize; i ++) {
            edictionHeader += " ";
        }
        for (int i = 0; i < isbnSize; i ++) {
            isbnHeader += " ";
        } 
        for (int i = 0; i < dateSize; i ++) {
            dateHeader += " ";
        }
        for (int i = 0; i < citySize; i ++) {
            cityHeader += " ";
        }
        
        String header = "= " + idHeader + " = " + nameHeader + " = " + authorHeader + " = " + edictionHeader + " = " + isbnHeader + " = " + dateHeader + " = " + cityHeader + " ="; 
        String line = "";
        for (int i = 0; i < header.length() + 1; i ++) {
            line += "=";
        }
        
        System.out.println(line);
        System.out.println(header);
        System.out.println(line);
        
        for (Book book : catalog.getBooks()) {
            String id = String.valueOf(book.getId());
            String name = book.getName();
            String author = book.getAuthors();
            String ediction = book.getEdiction();
            String isbn = book.getIsbn();
            String date = book.getDate();
            String city = book.getCity();
            
            while (id.length() < idHeader.length()) {
                id += " ";
            }
            while (name.length() < nameHeader.length()) {
                name += " ";
            }
            while (author.length() < authorHeader.length()) {
                author += " ";
            }
            while (ediction.length() < edictionHeader.length()) {
                ediction += " ";
            }
            while (isbn.length() < isbnHeader.length()) {
                isbn += " ";
            }
            while (date.length() < dateHeader.length()) {
                date += " ";
            }
            while (city.length() < cityHeader.length()) {
                city += " ";
            }
            
            String r = "= " + id + " = " + name + " = " + author + " = " + ediction + " = " + isbn + " = " + date + " = " + city + " =";
            
            System.out.println(r);
        }
        
        System.out.println(line);
      
        System.out.println("\n");
    }
    
    private void printBookInfo(Book book) {
        String bookInfo = book.getId() + "\t" + book.getName() + 
            "\t" + book.getAuthors() + "\t" + book.getEdiction() + "\t" + 
            book.getIsbn() + "\t" + book.getDate() + "\t" + book.getCity();
        System.out.println(bookInfo);
    }
    
    private void printBooksInfo(ArrayList<Book> books) {
        System.out.println("ID      Nombre      Autores     Ediccion        ISBN        Fecha       Ciudad");
        
        for  (Book book: books){
            this.printBookInfo(book);
        }
    }
    
    public void searchById(Catalog catalog) {
        this.print("Buscar libro por ID");
        
        int id = this.returnIntValue("Introduzca el ID del libro: ");
        
        int indexCatalog = LibraryController.catalogs.indexOf(catalog);
        
        for (Book book : LibraryController.catalogs.get(indexCatalog).getBooks()) {
            if (book.getId() == id) {
                this.print("Resultado");
                System.out.println("ID      Nombre      Autores     Ediccion        ISBN        Fecha       Ciudad");
        
                this.printBookInfo(book);
                return;
            }
        }
        this.print("No se ha encontrado el libro seleccionado");
    }
    
    public void searchProductByName(Catalog catalog) {
        this.print("Buscar libro por nombre");
        
        String name = this.returnStringValue("Introduzca el nombre del libro: ");
        
        ArrayList<Book> booksFinded = null;
       
        int indexCatalog = LibraryController.catalogs.indexOf(catalog);
        
        for (Book book : LibraryController.catalogs.get(indexCatalog).getBooks()) {
            if (book.getName().contains(name)) {
                booksFinded.add(book);
            }
        }
        
        if (booksFinded != null) {
            this.print("Resultado");
            
            this.printBooksInfo(booksFinded);
        } else {
            this.print("No se ha encontrado ningun libro con este nombre.");
        }
    }
    
    public void searchProductByISBN(Catalog catalog) {
        this.print("Buscar libro por ISBN");
        
        String isbn = this.returnStringValue("Introduzca el ISBN del libro: ");
        
        int indexCatalog = LibraryController.catalogs.indexOf(catalog);
        
        for (Book book : LibraryController.catalogs.get(indexCatalog).getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                this.print("Resultado");
                System.out.println("ID      Nombre      Autores     Ediccion        ISBN        Fecha       Ciudad");
        
                this.printBookInfo(book);
                return;
            }
        }
        this.print("No se ha encontrado el libro seleccionado");
    }
    
    // Reportes
    public void showReportMenu() {
        
        int option;
        
        this.print("============ Menú de reportes ============");
        System.out.println("1. Reporte en PDF.");
        System.out.println("2. Reporte en EXCEL.");
        System.out.println("3. Reprote en CSV.");
        
        option = this.returnIntValue("Seleccione una opción del menú: ");
        
        if (this.validateOptions(option, 1, 3)) {
            switch (option) {
                case 1:
                    List<String> data = new ArrayList<>();
                    for (Catalog catalog : LibraryController.catalogs) {
                        for (Book book : catalog.getBooks()) {
                            String[] _data = {catalog.getName(), book.getName(), book.getAuthors()};
                            data.add(catalog.getName());
                            data.add(book.getName());
                            data.add(book.getAuthors());
                        }
                    }
                    PDFReport pdf = new PDFReport();
                    pdf.generateReport(data);
                    break;
                case 2:
                    break;
                case 3:
                    break;            
            }
        } else {
            System.out.println("Opción incorrecta.");
            this.showReportMenu();
        }
    }
}