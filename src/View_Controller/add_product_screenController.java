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

/** This class is the controller for the add product screen.*/
public class add_product_screenController implements Initializable {


    @FXML
    private AnchorPane addproductscreen;

    @FXML
    private TextField addproductid;

    @FXML
    private TextField addproductname;

    @FXML
    private TextField addproductinv;

    @FXML
    private TextField addproductprice;

    @FXML
    private TextField addproductmax;

    @FXML
    private TextField addproductmin;

    @FXML
    private TextField addproductsearch;

    @FXML
    private TableView<Part> addproductpartdata;

    @FXML
    private TableColumn<Part, Integer> addpartid;

    @FXML
    private TableColumn<Part, String> addpartname;

    @FXML
    private TableColumn<Part, Integer> addpartinventory;

    @FXML
    private TableColumn<Part, Double> addpartprice;

    @FXML
    private TableView<Part> addproductassociatedpartdata;

    @FXML
    private TableColumn<Part, Integer> addassociatedpartid;

    @FXML
    private TableColumn<Part, String> addassociatedpartname;

    @FXML
    private TableColumn<Part, Integer> addassociatedpartinventory;

    @FXML
    private TableColumn<Part, Double> addassociatedpartprice;

    Parent scene;
    Stage stage;

    public Part addPartToProduct;

    private ObservableList<Part>associatedParts = FXCollections.observableArrayList();

    /**This will take the selected part from the part table and add it to the associated part table for the product.*/
   public void addproductaddhandler(ActionEvent event) {

        Part part = addproductpartdata.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        addproductassociatedpartdata.setItems(associatedParts);
    }

    /**This button will return to the main screen. */
   public void addproductcancelhandler(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/main_screen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


/**Disassociates a part from the product and remove the selection from associated table.*/
    @FXML
    public void addproductremovehandler(ActionEvent event) throws IOException {
        Part selectedAssociatedPart = addproductassociatedpartdata.getSelectionModel().getSelectedItem();

        if(selectedAssociatedPart == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select an Associated Part first!");
            alert.showAndWait();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to remove the associated part?");
        alert.showAndWait();

        associatedParts.remove(selectedAssociatedPart);


    }

/**RUNTIME ERROR occurred during saving a product and it was solved using a try/catch method to validate input and alert the user.*/
    @FXML
    public void addproductsavehandler(ActionEvent event) throws IOException {
        int id = 0;
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() > id)
                id = product.getId();
        }

        try {
            addpartid.setText(String.valueOf(++id));
            String name = addproductname.getText();
            int stock = Integer.parseInt(addproductinv.getText());
            double price = Double.parseDouble(addproductprice.getText());
            int min = Integer.parseInt(addproductmin.getText());
            int max = Integer.parseInt(addproductmax.getText());

            if (max < min) {
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

            Product newProduct = new Product(id, " ", 0.0, 0, 0, 0);
            newProduct.setName(addproductname.getText());
            newProduct.setPrice(Double.parseDouble(addproductprice.getText()));
            newProduct.setStock(Integer.parseInt(addproductinv.getText()));
            newProduct.setMin(Integer.parseInt(addproductmin.getText()));
            newProduct.setMax(Integer.parseInt(addproductmax.getText()));

            Inventory.addProduct(newProduct);

            for(Part part : associatedParts)
            {
                newProduct.addAssociatedPart(part);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/main_screen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Alert");
            alert.setContentText("Please enter correct values");
            alert.showAndWait();
        }
    }

        @FXML
    void addproductsearchHandler(ActionEvent event) {

    }

    /** This will initialize and set the cell values and populate the tables.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addproductpartdata.setItems(Inventory.getAllParts());
        addproductassociatedpartdata.setItems(associatedParts);


        addpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
        addpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
        addpartinventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addpartprice.setCellValueFactory(new PropertyValueFactory<>("price"));

        addassociatedpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
        addassociatedpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
        addassociatedpartinventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addassociatedpartprice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**Search part in the part table on product screen.*/
    public void addproductsearchbutton(ActionEvent actionEvent) {

        String s = addproductsearch.getText();

        ObservableList<Part> parts = searchParts(s);

        addproductpartdata.setItems(parts);
        addproductsearch.setText("");
    }

  /**Searches through the parts by partial or full name and ID, it refreshes if none is found.*/
    private ObservableList<Part> searchParts(String partialPart) {
        ObservableList<Part> nameParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        String s = addproductsearch.getText();

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
