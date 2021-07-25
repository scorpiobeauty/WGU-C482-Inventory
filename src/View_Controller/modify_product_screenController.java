package View_Controller;


import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.*;

/** This class is the controller for the modify product screen.*/
public class modify_product_screenController implements Initializable {

    Parent scene;
    Stage stage;


    private Product selectedProduct;
    int index;

    private ObservableList<Part> associatedParts = observableArrayList();
    private ObservableList<Part> oldAssocPart = FXCollections.observableArrayList();


    private ObservableList<Product> products = observableArrayList();

    
    @FXML
    private AnchorPane modifyproductscreen;

    @FXML
    private TextField modifyproductid;

    @FXML
    private TextField modifyproductname;

    @FXML
    private Button searchmodifypartsbutton;

    @FXML
    private TextField modifyproductinv;

    @FXML
    private TextField modifyproductprice;

    @FXML
    private TextField modifyproductmax;

    @FXML
    private TextField modifyproductmin;

    @FXML
    private TextField modifyproductsearch;

    @FXML
    private TableView<Part> modifyproductpartdata;

    @FXML
    private TableColumn<Part, Integer> modpartid;

    @FXML
    private TableColumn<Part, String> modpartname;

    @FXML
    private TableColumn<Part, Integer> modpartinventory;

    @FXML
    private TableColumn<Part, Double> modpartprice;



    @FXML
    private TableView<Part> modifyproductassociatedpartdata;

    @FXML
    private TableColumn<Part, Integer> modassociatedpartid;

    @FXML
    private TableColumn<Part, String> modassociatedpartname;

    @FXML
    private TableColumn<Part, Integer> modassociatedinventory;

    @FXML
    private TableColumn<Part, Double> modassociatedprice;





/**This will take the selected part from the part table and add it to the associated part table for the product.*/
    @FXML
    public void modifyproductadd(ActionEvent event) {

        Part part = modifyproductpartdata.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        modifyproductassociatedpartdata.setItems(associatedParts);
    }


    /**This button will return to the main screen. */
    @FXML
    public void modifyproductcancelhandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/main_screen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /** Disassociates a part from the product and remove the selection from associated table. */
    @FXML
    public void modifyproductremove(ActionEvent event) throws IOException {

            Part selectedAssociatedPart = modifyproductassociatedpartdata.getSelectionModel().getSelectedItem();

            if (selectedAssociatedPart == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You must select an Associated Part first!");
                alert.showAndWait();

                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove the associated part?");
            alert.showAndWait();

            associatedParts.remove(selectedAssociatedPart);
            modifyproductassociatedpartdata.setItems(associatedParts);



    }

    /**RUNTIME ERROR occurred during saving a product and it was solved using a try/catch method to validate input and alert the user. This method updates the current part and add it as a new product and deletes the old one.*/
    @FXML
    public void modifyproductsavehandler(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(modifyproductid.getText());
            String name = modifyproductname.getText();
            int stock = Integer.parseInt(modifyproductinv.getText());
            double price = Double.parseDouble(modifyproductprice.getText());
            int min = Integer.parseInt(modifyproductmin.getText());
            int max = Integer.parseInt(modifyproductmax.getText());


            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Minimum must have a value less than maximum");
                alert.showAndWait();
                return;
            }

            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inv value must be between min and max");
                alert.showAndWait();
                return;
            }

            Product modProduct = new Product(id,name,price,stock,min,max);
            for (Part part : associatedParts) {
                modProduct.addAssociatedPart(part);

            }

            Inventory.addProduct(modProduct);
            Inventory.deleteProduct(selectedProduct);


        } catch (NumberFormatException e) {

            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Alert");
            alert.setContentText("Please enter correct values");
            alert.showAndWait();
            e.printStackTrace();
            return;
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/main_screen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
        @FXML
    void modifyproductsearchhandler(ActionEvent event) {

    }

/** This is a method created to pass data between this and main controller. */
    public void getSelectedProduct(Product product){
        selectedProduct = product;

        modifyproductid.setText(Integer.toString(product.getId()));
        modifyproductname.setText(product.getName());
        modifyproductprice.setText(Double.toString(product.getPrice()));
        modifyproductinv.setText(Integer.toString(product.getStock()));
        modifyproductmax.setText(Integer.toString(product.getMax()));
        modifyproductmin.setText(Integer.toString(product.getMin()));
        associatedParts = product.getAllAssociatedParts();
        modifyproductassociatedpartdata.setItems(associatedParts);
        //modifyproductassociatedpartdata.setItems(product.getAllAssociatedParts());

    }

    /**Initialize controllers and populates the part and product tables.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = new Product(0, null, 0.0, 0,0,0);
        associatedParts = selectedProduct.getAllAssociatedParts();

        modpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
        modpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
        modpartprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modpartinventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyproductpartdata.setItems(Inventory.getAllParts());

        modassociatedpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
        modassociatedpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
        modassociatedprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modassociatedinventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyproductassociatedpartdata.setItems(selectedProduct.getAllAssociatedParts());


    }
/** Search for parts on the modify product screen.*/
    public void searchmodifyparts(ActionEvent actionEvent) {

        String s = modifyproductsearch.getText();

        ObservableList<Part> parts = searchParts(s);

        modifyproductpartdata.setItems(parts);
        modifyproductsearch.setText("");
    }

    /**Searches through the parts by partial or full name and  ID, it will refresh if none is found.**/
    private ObservableList<Part> searchParts(String partialPart) {
        ObservableList<Part> nameParts = observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        String s = modifyproductsearch.getText();

        for (Part p : allParts) {
            if (String.valueOf(p.getId()).contains(s) || p.getName().contains(partialPart)) {
                nameParts.add(p);
            }

        }
        if (nameParts.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }
        return nameParts;

    }
}


