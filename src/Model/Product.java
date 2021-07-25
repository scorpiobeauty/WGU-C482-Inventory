package Model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**This class contains all Product details.*/
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**Product constructor*/
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


/**Product ID getter.*/
    public int getId() {
        return id;
    }
    /**Product ID setter.*/
    public void setId(int id) {
        this.id = id;
    }
    /**Product name getter.*/
    public String getName() {
        return name;
    }
    /**Product name setter.*/
    public void setName(String name) {
        this.name = name;
    }
    /**Product price getter.*/
    public double getPrice() {
        return price;
    }

    /**Product price setter.*/
    public void setPrice(double price) {
        this.price = price;
    }
    /**Product stock getter.*/
    public int getStock() {
        return stock;
    }
    /**Product stock setter.*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**Product min getter.*/
    public int getMin() {

        return min;
    }
    /**Product min setter.*/
    public void setMin(int min) {
        this.min = min;
    }

    /**Product max getter.*/
    public int getMax() {

        return max;
    }

    /**Product max setter.*/
    public void setMax(int max) {
        this.max = max;
    }


    /**Product associated part setter.*/
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**Product associated part deletes associated parts.*/
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else{
            return false;
        }
    }
    /**Product associated part getter.*/
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }



    public void remove(Part part) {
    }
}
