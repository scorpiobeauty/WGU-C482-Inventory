package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**This class contains all Inventory details with getters and setters.*/
public class Inventory {

    /**All Parts in the inventory.*/
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**All Products in the inventory.*/
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**Add parts to inventory.*/
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**Add products to inventory.*/
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

/**This method searches through the parts and return if found  by name.*/
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> partNameFound = FXCollections.observableArrayList();

        for (Part part : allParts){
            if (part.getName().equals(partName)) {
                partNameFound.add(part);
            }
            return partNameFound;


        }
        return partNameFound;
    }

    /**This method searches through the parts and return if found by ID.*/
    public static Part lookupPart(int partID) {
        Part partIdFound = null;

        for(Part part : allParts){
            if(part.getId() == partID){
                partIdFound = part;
            }
        }
        return partIdFound;
    }

    /**This method searches through the product and return if found by ID.*/
    public static Product lookupProduct(int productID) {
        Product productIdFound = null;

        for(Product product : allProducts){
            if(product.getId() == productID){
                productIdFound= product;
            }
        }
        return productIdFound;
    }


    /**This method searches through the product and return if found by name.*/
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productNameFound = FXCollections.observableArrayList();

        for(Product product : allProducts){
            if(product.getName().equals(productName)){
                productNameFound.add(product);
            }
        }
        return productNameFound;
    }


    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);


    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /** Deletes a selected Part.*/
    public static boolean deletePart(Part selectedPart) {

        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Delete a selected product. */
    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else{
            return false;
        }
    }

    /** Returns all parts. */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /** Returns all products. */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }



}
