package controller;

import java.util.Scanner;
import java.util.Set;
import model.Product;

public class Validation {
    private static Scanner sc = new Scanner(System.in);
    
    public static int getInt(String msg, int min, int max) {
        if(min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        
        int n;
        while(true) {
            try {
                System.out.print(msg);
                n = Integer.parseInt(sc.nextLine().trim());
                if(min <= n && n <= max) break;
                throw new NumberFormatException();
            } catch(NumberFormatException ex) {
                System.err.println("Please enter an integer in range: " + min + " -> " + max + " " + "! ");                
            }
        }
        return n;       
    }
    
    public static double getDouble(String msg, double min, double max) {
        if(min > max) {
            double tmp = min;
            min = max;
            max = tmp;
        }
         
        double n;
        while(true) {
            try {
                System.out.print(msg);
                n = Double.parseDouble(sc.nextLine().trim());
                if(min <= n && n <= max) break;
                throw new NumberFormatException();
            } catch(NumberFormatException ex) {
                System.err.println("Please enter a real number in range: " + min + " -> " + max + " " + "! ");  
            }
        }
        return n;       
    }
    
    public static String getID(String msg, Set<String> existIDs) {
        String s;
                
        while(true) {
            System.out.print(msg);
            s = sc.nextLine().trim();
            
            if(s.isEmpty()) {
                System.err.println("Please enter valid product ID! ");
                continue;
            }
            
            if(existIDs.contains(s)) {
                System.err.println("ID " + s + " is already existed. Please enter another ID! ");
                continue;
            }
            existIDs.add(s);
            return s;    
        }

    } 
   
    public static String getName(String msg, int length, String regex) {
        String s;
        while(true) {
            System.out.print(msg);
            s = sc.nextLine();
            if(!s.isEmpty()) {
                if(s.length() >= length && !s.contains(regex)) break;           
            }
            System.err.println("Please enter a name that must be at least " + length
                               + " characters and no spaces! : ");
        }
        return s;
    }
    
    public static String getStatus(String msg) {
        String s;
        while(true) {
            System.out.print(msg);
            s = sc.nextLine().trim();
            if(!s.isEmpty()) {
                if(s.equalsIgnoreCase("Available") || s.equalsIgnoreCase("Not Available")
                        || s.equalsIgnoreCase("A") || s.equalsIgnoreCase("NA")) break;
            }
            System.err.println("Please enter just (Available / Not Available) or (A / NA)! : ");
        }
        return s;
    } 
    
    public static String getYN(String msg) {
        String s;
        while(true) {
            System.out.print(msg);
            s = sc.nextLine().trim();
            if(!s.isEmpty()) {
                if(s.equalsIgnoreCase("y") || s.equalsIgnoreCase("n")) break;
            }
            System.err.print("Please enter just Y or N !!" + " " + ": ");
        }
        return s;
    }   
    
    public static String getNameToUpdate(String msg, String s, Product p, int length, String regex) {
        System.out.print(msg);
        s = sc.nextLine();
        
        String n = "";
        while(true) {
            try {
                if(!s.isEmpty()) {
                    n = s;
                    if(n.length() < length || s.contains(regex)) throw new NumberFormatException();
                    p.setProductName(n);
                }
                break;
            } catch(NumberFormatException ex) {
                System.err.println("Please enter a name that must be at least " + length
                               + " characters and no spaces! " + " " + ": ");
                s = sc.nextLine();
            } 
        }
        return n;
    }
    
    public static double getUnitPriceToUpdate(String msg, String s, Product p, double min, double max) {
        if(min > max) {
            double tmp = min;
            min = max;
            max = tmp;
        }
        System.out.print(msg);
        s = sc.nextLine().trim();
        
        double n = 0;
        
        while(true) {
            try {
                if(s.isEmpty()) {
                    n = p.getUnitPrice();
                    break;
                }
                n = Double.parseDouble(s);
                if(n < min || n > max) throw new NumberFormatException();
                break;
            } catch(NumberFormatException ex) {
                System.err.println("Please enter a real number in range " + min + " -> " + max + ": "); 
                s = sc.nextLine();
            }
        }
        p.setUnitPrice(n);
        return n;       
    } 
    
    public static int getQuantityToUpdate(String msg, String s, Product p, double min, double max) {
        if(min > max) {
            double tmp = min;
            min = max;
            max = tmp;
        }
        System.out.print(msg);
        s = sc.nextLine().trim();
        
        int n = 0;
        
        while(true) {
            try {
                //if input is empty, keep old value
                if(s.isEmpty()) {
                    n = p.getQuantity();
                    break;
                }
                n = Integer.parseInt(s);
                if(n < min || n > max) throw new NumberFormatException();
                break;
            } catch(NumberFormatException ex) {
                System.err.println("Please enter a integer number in range: " + min + " -> " + max + ": "); 
                s = sc.nextLine();
            }
        }
        p.setQuantity(n);
        return n;       
    } 
    
    public static String getStatusToUpdate(String msg, String s, Product p) {
        System.out.print(msg);
        s = sc.nextLine().trim();
        
        String n = "";
        
        while(true) {
            try {
                if(!s.isEmpty()) {
                    n = s;
                    
                    if(!n.equals("Available") && !n.equals("Not Available")
                       && !n.equals("A") && !n.equals("NA")) throw new NumberFormatException();
                    //if (!n.matches("(?i)(available|not available|A|NA)")) throw new NumberFormatException();
                    p.setStatus(n);
                }
                break;
            } catch(NumberFormatException ex) {
                System.err.print("Please enter just Available or Not Available or (N / NA)! : ");
                s = sc.nextLine().trim();         
            }
        }
        return n;
    }
    
}
