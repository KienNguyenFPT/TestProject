package view;

import controller.*;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private static String[] ops = {
        "Create product",
        "Check exist product",
        "Search Product's information by Containing Name",
        "Update Product",
        "Update Containing Name Product to a constant value",
        "Delete Product",
        "Print list of Products",
        "Quit"
    };

    private static int getChoice() {
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < ops.length; i++) {
            System.out.println((i + 1) + ". " + ops[i]);   
        }
        
        return Validation.getInt("Your choice: ", 1, ops.length);
    }
    
    public static void display() throws IOException {
        ProductManagement p = new ProductManagement();
        int choice;
        do {
            System.out.println("=== PRODUCT MANAGEMENT ===");
            choice = getChoice();
            switch(choice) {
                case 1:
                    p.createProduct();
                    break;
                case 2:
                    p.checkExistProduct();
                    break;
                case 3:
                    p.search();
                    break;
                case 4:
                    p.updateProduct();
                    break;
                case 5:
                    p.updateContainingNameProduct();
                    break;
                case 6:
                    p.deleteProduct();
                    break;
                case 7:
                    //p.printList(p.getpList());
                    p.printList();
                    break;
                case 8:
                    System.exit(0);
            }      
        } while(choice != ops.length);
    }
    
}
