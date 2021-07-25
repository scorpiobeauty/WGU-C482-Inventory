package View_Controller;

import Model.*;
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

/** This class is the controller for the main screen.*/
public class main_screenController implements Initializable {



    Parent scene;
    Stage stage;

  Product selectedProduct;


    @FXML
    private AnchorPane parts;

    @FXML
    private Button mainpartsearchbutton;

    @FXML
    private Button mainproductsearchbutton;

    @FXML
    private TableView<Part> partstable;

    @FXML
    private TableColumn<Part, Integer> partid;


    @FXML
    private TableColumn<Part, String> partname;

    @FXML
    private TableColumn<Part, Integer> partsinventorylevel;

    @FXML
    private TableColumn<Part, Double> partsprice;

    @FXML
    private TableView<Product> productstable;

    @FXML
    private TableColumn<Product, Integer> productid;

    @FXML
    private TableColumn<Product, String> productname;

    @FXML
    private TableColumn<Product, Integer> productinventorylevel;

    @FXML
    private TableColumn<Product, Double> productprice;

    @FXML
    private Label messagebar;

    @FXML
    private TextField partsearch;

    @FXML
    private TextField productsearch;

    @FXML
    void partsearchbox(ActionEvent event) {
        System.out.println("part search box clicked");

    }


    @FXML
    void productsearchbox(ActionEvent event) {
        System.out.println("product search box clicked");


    }

    /** This button will take you to the add part screen.*/
    @FXML
    public void mainaddparthandler(ActionEvent event) throws IOException {
        System.out.println("On part Add Clicked");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/addpartscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This button will take you to the add product screen. */
    @FXML
    public void mainaddproducthandler(ActionEvent event) throws IOException {
        System.out.println("On part Add Clicked");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/add_product_screen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This button will delete a part from main.*/
    @FXML
    public void maindeleteparthandler(ActionEvent event) {
        System.out.println(" part delete Clicked");
        Part selectedPart = partstable.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Part first!");
            alert.showAndWait();

            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to delete the selected part?");
        alert.showAndWait();


        Inventory.deletePart(selectedPart);

    }


    /** This button will delete a product from main if it does not have an associated part. */
    @FXML
   public void maindeleteproducthandler(ActionEvent event) {
        System.out.println("product delete Clicked");
        Product selectedProduct = productstable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Product first!");
            alert.showAndWait();

            return;
        }

        ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
        if(associatedParts.size() >= 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("This Product has an Associated Part. Cannot Delete!");
            alert.showAndWait();
            return;

        }
        else {
            Inventory.deleteProduct(selectedProduct);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected product?");
            alert.showAndWait();
            return;
        }


    }

    /** This button will exit the application. */
    @FXML
    public void mainexithandler(ActionEvent event) {
        System.out.println("system exit button clicked");
        System.exit(0);

    }

    /** This button will take you to the modify part screen.*/
    @FXML
   public void mainmodifyparthandler(ActionEvent event) throws IOException {
        System.out.println("main part modify Clicked");

        Part selectedPart = partstable.getSelectionModel().getSelectedItem();
        //int index = partstable.getSelectionModel().getSelectedIndex();

        if(selectedPart == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Part first!");
            alert.showAndWait();

            return;
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/modifypartscreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        modifypartscreencontroller controller = loader.getController();
        controller.getSelectedPart(partstable.getSelectionModel().getSelectedItem());
        stage.show();
    }



    /** This button will take you to the modify product screen.*/
    @FXML
    public void mainmodifyproducthandler(ActionEvent event) throws IOException {
        System.out.println("main product modify Clicked");
        Product selectedProduct = productstable.getSelectionModel().getSelectedItem();


        if(selectedProduct == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Product first!");
            alert.showAndWait();

            return;
        }


        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/modify_product_screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        modify_product_screenController controller = loader.getController();
        controller.getSelectedProduct(productstable.getSelectionModel().getSelectedItem());
        stage.show();

    }



//test data
    private static boolean firstTime = true;
/**Test data for the application.*/
    public void addTestData(){
        if(!firstTime){
            return;
        }
        firstTime = false;

        InHouse tire = new InHouse(1,"Tire", 50.55, 2, 1, 10,11);
        Inventory.addPart(tire);

        Outsourced headlight = new Outsourced(11,"Headlight", 22.22, 6, 1,8,"Light CO");
        Inventory.addPart(headlight);

        Product bike = new Product(111, "Bike", 100.33, 3, 1, 9);
        Inventory.addProduct(bike);

        Product scooter = new Product(3,"Scooter", 211.44, 4,1,9);
        scooter.addAssociatedPart(tire);
        Inventory.addProduct(scooter);

    }


    /**Initialize controllers and populates the part and product tables*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTestData();

        //parts
        partstable.setItems(Inventory.getAllParts());
        partid.setCellValueFactory(new PropertyValueFactory<>("id"));
        partname.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsinventorylevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsprice.setCellValueFactory(new PropertyValueFactory<>("price"));




        //products
        productstable.setItems(Inventory.getAllProducts());
        productid.setCellValueFactory((new PropertyValueFactory<>("id")));
        productname.setCellValueFactory(new PropertyValueFactory<>("name"));
        productinventorylevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productprice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

/**Search a part in the main screen table.*/
    public void mainpartsearchbutton(ActionEvent actionEvent) {
        String s = partsearch.getText();

        ObservableList<Part> parts = searchParts(s);

        partstable.setItems(parts);
        partsearch.setText("");




    }
    /**Search a part in the main screen table.*/
    private ObservableList<Part> searchParts(String partialPart) {

        ObservableList<Part> nameParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        String s = partsearch.getText();

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






    /**Search a product in the main screen table.*/
    public void mainproductsearchbutton(ActionEvent actionEvent) {
        String s = productsearch.getText();

        ObservableList<Product> products = searchProduct(s);

        productstable.setItems(products);
        productsearch.setText("");
    }
    /**Search a product in the main screen table.*/
    private ObservableList<Product> searchProduct(String partialProduct) {
        ObservableList<Product> nameProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        String s = productsearch.getText();

        for (Product p : allProducts) {
            if (String.valueOf(p.getId()).contains(s) || p.getName().contains(partialProduct)) {
                nameProducts.add(p);
            }

        }
        if (nameProducts.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Part not found");
            alert.showAndWait();

        }
        return nameProducts;
    }


}



