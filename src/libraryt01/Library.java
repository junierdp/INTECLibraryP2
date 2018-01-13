package libraryt01;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author JunierDP
 * 
 * Hacer una libreria que guarde lo siguiente:
 * 
 * Libro:
 * - ID
 * - Autor (es)
 * - Nombre
 * - Edicion
 * - ISBN
 * - Fecha
 * - Ciudad
 * 
 * Debe contener el siguente menu:
 * 
 * 1. Agregar libro
 * 2. Editar libro
 * 3. Borrar libro
 * 4. Listar todos los libros
 * 5. Buscar libro por ID
 * 6. Buscar libro por nombre
 * 7. Buscar libro por ISBN
 * 8. Salir
 */

public class Library { 
    
    Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {     
        new Library().showMainMenu();
    } 
    
    public void showMainMenu() {
        int option = 0;
        do {
            LibraryController.get.print("================ MENU PRINCIPAL =================");
            
            System.out.print("1. Agregar catalogo. \n2. Editar catalogo. \n3. Administrar catalogo. "
                    + "\n4. Mostrar catalogos. \n5. Eliminar catalogo. "
                    + "\n6. Reportes. "
                    + "\n7. Importar. "
                    + "\n8. Exportar. "
                    + "\n9. Salir. \n");
            System.out.println("Seleccione una opción del menu: ");
            
            option = this.returnIntValue();
            
            if (LibraryController.get.validateOptions(option,1,6)) {
                this.mainMenuActions(option);
            }else{
                LibraryController.get.print("Elija un numero entre las opciones del menu.");
            }
        } while (option != 9);
    }
        
    public void mainMenuActions(int option) {
        switch (option) {
            case 1: 
                //Agregar catalogo
                LibraryController.get.addCatalog();
                LibraryController.get.saveData();
                break;
            case 2:
                //Editar catalogo
                LibraryController.get.editCatalog();
                LibraryController.get.saveData();
                break;
            case 3:
                //Admistrar catalogo
                LibraryController.get.manageCatalog();
                break;
            case 4: 
                //Mostrar catalogos
                LibraryController.get.showCatalogs();
                break;
            case 5: 
                LibraryController.get.deleteCatalog();
                LibraryController.get.saveData();
                break;
            case 6: 
                LibraryController.get.showReportMenu();
                break;
            case 9:
                LibraryController.get.saveData();
                LibraryController.get.print("El programa se ha cerrado.");
                break;
        }
    }
    
    public int returnIntValue() {
        int intValue = 0;
        while (intValue == 0) {
            try {
                String value = sc.nextLine();
                intValue = Integer.valueOf(value);
            } catch (Exception e) {
                System.out.println("El valor tiene que ser un numero.");
                System.out.println("Seleccione una opción valida: ");
            }
        }
        return intValue;
    }
}
