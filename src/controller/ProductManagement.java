package controller;

import java.io.*;
import java.util.*;
import java.util.Collections;
import java.util.stream.*;
import model.Product;

//Update all product that contains Containing Name to constant Unit Price
//Modified available or not available
public class ProductManagement {
    private List<Product> pList;
    Set<String> existIDs = new HashSet<> ();
    Scanner sc = new Scanner(System.in);
    
    public ProductManagement() throws IOException {
        pList = new ArrayList<> ();
        loadDataFromFile();
    }

    public List<Product> getpList() {
        return pList;
    }
    
    Comparator<Product> pComparator = new Comparator<Product> () {
        @Override
        public int compare(Product p1, Product p2) {
            //Quantity descending
            int result = Integer.compare(p2.getQuantity(), p1.getQuantity());
            if(result == 0) {
                //UnitPrice ascending
                result = Double.compare(p1.getUnitPrice(), p2.getUnitPrice());
            }
            return result;
        }
    };
     
    private void loadDataFromFile() throws IOException {
        // Load data from file.txt
        File f = new File("Product.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] products = line.split("[-]");
                String pID = products[0].trim();
                String pName = products[1].trim();
                double pUnitPrice = Double.parseDouble(products[2].trim());
                int pQuantity = Integer.parseInt(products[3].trim());
                String pStatus = products[4].trim();
                existIDs.add(pID);
                pList.add(new Product(pID, pName, pUnitPrice, pQuantity, pStatus));
            }
        } catch(IOException ex) {}
        
        /* // Load data from file.dat
        File f = new File("Product.dat");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(f, true))) {
            String pID = null, pName = null, pStatus = null;
            double pUnitPrice = 0;
            int pQuantity = 0;
            
            String product = pID + " - " + pName + " - " + pUnitPrice + " - " + pQuantity + " - " + pStatus;
            bw.write(product);
            bw.newLine(); 
        } catch(IOException ex) {} */
    }
    
    private void saveToFile() throws IOException {
        //Save data to file.txt
        File f = new File("Product.txt");
        try(FileWriter fw = new FileWriter(f); BufferedWriter bw = new BufferedWriter(fw)) {
            PrintWriter pw = new PrintWriter(bw);
            for(Product p : pList) {
            pw.println(p.getProductID() + " - " + 
                    p.getProductName() + " - " +
                    p.getUnitPrice() + " - " + 
                    p.getQuantity() + " - " + 
                    p.getStatus());
            }
        }     
     
        /* //Save data to file.dat
        File f = new File("Product.dat");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(f, true))) {
            for(Product p : pList) {
                bw.write(p.getProductID() + " - " + 
                         p.getProductName() + " - " +
                         p.getUnitPrice() + " - " + 
                        p.getQuantity() + " - " + 
                        p.getStatus());
                bw.newLine();
            }
        } */
    }
    
    public void createProduct() throws IOException {
        Scanner sc = new Scanner(System.in);
        String pID, pName, pStatus;
        double pUnitPrice;
        int pQuantity;
        
        pID = Validation.getID("Enter product ID: ", existIDs);
        pName = Validation.getName("Enter product name: ", 5, " ");
        pUnitPrice = Validation.getDouble("Enter product unit price: ", 0, 10000);
        pQuantity = Validation.getInt("Enter product quantity: ", 0, 1000);
        pStatus = Validation.getStatus("Enter product status (Just press Available/Not Available/A/NA): ");
        
        pList.add(new Product(pID, pName, pUnitPrice, pQuantity, pStatus));
        saveToFile(); 
    }
    
    public void checkExistProduct() {
        if(pList.isEmpty()) {
            System.out.println("Have no any Product");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product ID to check: ");
        String productID = sc.nextLine().trim();  
       
        if(idExist(productID)) System.out.println("Exist Product");
        else System.out.println("No Product Found!");
    }
    
    public boolean idExist(String id) {
        return pList.stream().anyMatch(p -> p.getProductID().equals(id));
    }
    
    public void search() {
        if(pList.isEmpty()) System.out.println("Have no any Product");
        else {
            String searchName = Validation.getName("Enter search string: ", 1, " ");
            List<Product> res = searchByContainingName(pList, searchName);
            if(res.isEmpty()) {
                System.out.println("No product contains " + searchName + " in their name! ");
                return;
            }
            res.sort(Comparator.comparing(Product::getProductName));
            res.stream().forEach(System.out::println);
        }
    }
    
    public List<Product> searchByContainingName(List<Product> searchList, String name) {
        return searchList.stream().filter(p -> p.getProductName().contains(name))
                                   .collect(Collectors.toList());
    }
    
    public void updateProduct() throws IOException {
        if(pList.isEmpty()) {
            System.out.println("Have no any Product");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        String pID = sc.nextLine().trim();
        
        if(!idExist(pID)) System.out.println("Product does not exist");
        else {
            Product p = null; // p is product to upadate
            for(Product o : pList) {
                if(o.getProductID().equals(pID)) {
                    p = o;
                    break;
                }
            }            
           
            String pName = "";
            String pUnitPrice = "";
            String pQuantity = "";
            String pStatus = "";
            
            String name = Validation.getNameToUpdate("Enter new product Name (Leave blank to keep old value): ",
                                                      pName, p, 5, " ");
            double unitPrice = Validation.getUnitPriceToUpdate("Enter new product Unit price (Leave blank to keep old value): ",
                                                                pUnitPrice, p, 0, 10000);
            int quantity = Validation.getQuantityToUpdate("Enter new product Quantity (Leave blank to keep old value): ", 
                                                           pQuantity, p, 0, 1000);
            String status = Validation.getStatusToUpdate("Enter new product Status (Leave blank to keep old value): ",
                                                          pStatus, p);
            
            // update pList with new product information
            for(int i = 0; i < pList.size(); i++) {
                if(pList.get(i).getProductID().equals(p.getProductID())) {
                    pList.set(i, p);
                }
            }
            
            //Check result of update: SUCCESS or FAIL!
            Product productUpdated = null;
            for(Product o : pList) {
                if(o.getProductID().equals(pID)) productUpdated = o;
            }
            if(productUpdated != null) {
                System.out.println("SUCCESS: Product with ID " + pID + " has been updated!");
            }
            else {
                System.out.println("FAIL: Product with ID " + pID + " has not been updated!");
            }
      }
      saveToFile();      
    }
    double newVal = 0;
    public void updateContainingNameProduct() throws IOException {  
        System.out.print("Enter search string: ");
        String name = sc.nextLine();
        List<Product> productsToUpdate = searchByContainingName(pList, name);
        
        if(productsToUpdate.isEmpty()) {
            System.out.println("No product contains " + name + " in their name! ");
        } else{
            System.out.println("Press 1 to update new UnitPrice and press 2 to update new Quantity!");
            int op = sc.nextInt();
            if(op == 1) {
                System.out.print("Enter new UnitPrice: ");
                newVal = sc.nextDouble();
            }
            else if(op == 2) {
                System.out.println("Enter new Quantity");
                newVal = sc.nextDouble();
            }
            else {
                System.out.println("Invalid choice, please try again!");
                return;
            }
            productsToUpdate.forEach(p -> {
                if(op == 1)
                    p.setUnitPrice(newVal);
                else
                    p.setQuantity((int)newVal);
        });
        
        String updated = op == 1 ? "Unit Price" : "Quantity";
        System.out.println(productsToUpdate.size() + " product(s) with name containing: " + name + " have been updated to " + updated + " : " + newVal);
    }
    saveToFile();  
    }
    
    public void deleteProduct() throws IOException {
        if(pList.isEmpty()) {
            System.out.println("Have no any Product");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        String pID = sc.nextLine().trim();
        
        if(!idExist(pID)) {
            System.out.println("Product does not exist");
            return;
        }
         
        String status = Validation.getYN("Are you sure to delete the product that contains this ID? (Y/N): ");
        if(!status.equalsIgnoreCase("Y")) {
            System.out.println("FAIL: Product with ID " + pID + " has not been deleted!");
            return;
        }
        
        for(int i = 0; i < pList.size(); i++) {
            Product p = pList.get(i);
            if(p.getProductID().equals(pID)) pList.remove(i);
        }
        System.out.println("SUCCESS: Product with ID " + pID + " has been deleted!");
        saveToFile();
    }
   
    public void printList() {
        //Collections.sort(p, pComparator);
        //p.stream().forEach(System.out::println);
        for(Product p : pList) {
            System.out.println(p);
        }
    }
    
}
