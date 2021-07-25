package View_Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class is the controller for the add part screen.*/
public class addpartscreenController implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private AnchorPane addpartscreen;

    @FXML
    private RadioButton addpartinhousescreen;

    @FXML
    private ToggleGroup addpartbutton;

    @FXML
    private RadioButton addpartoursourcedscreen;

    /** This label will change to MachineID or Company Name. */
    @FXML
    private Label machineorcompanylabel;

    @FXML
    private TextField addpartid;

    @FXML
    private TextField addpartnamein;

    @FXML
    private TextField addpartinvin;

    @FXML
    private TextField addpartpricein;

    @FXML
    private TextField addpartmaxin;

    @FXML
    private TextField addpartmachineidin;

    @FXML
    private TextField addpartminin;

    /** This button returns to Main screen. */
    @FXML
    public void addpartcancelhandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();


        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/main_screen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Change field name to Machine ID for In-house. */
    @FXML
   public void addpartinhousescreen(ActionEvent event) {

        machineorcompanylabel.setText("Machine ID");

    }

    /** Change field name to Company Name for Outsourced. */
    @FXML
    public void addpartoutsourcedscreen(ActionEvent event) {

        machineorcompanylabel.setText("Company Name");

    }

    /**RUNTIME ERROR occurred during saving a part and it was solved using a try/catch method to validate input and alert the user. This adds a new part to the inventory.*/
    public void addpartsavehandler(ActionEvent event) throws IOException {
       int id = 0;
       for(Part part : Inventory.getAllParts()){
           if(part.getId() > id) {
               id = part.getId();
           }
       }

            try {

               String name = addpartnamein.getText();
               int stock = Integer.parseInt(addpartinvin.getText());
               double price = Double.parseDouble(addpartpricein.getText());
               int min = Integer.parseInt(addpartminin.getText());
               int max = Integer.parseInt(addpartmaxin.getText());

               if (max < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Minimum must have a value less than maximum");
                    alert.showAndWait();
                    return;
                }

                if(stock > max || stock < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Inv value must be between min and max");
                    alert.showAndWait();
                    return;
                }


                Part partnew;
                if(addpartinhousescreen.isSelected()) {
                    partnew = new InHouse(id, "", 0.0, 0, 0, 0, 0);
                    partnew.setName(addpartnamein.getText());
                    partnew.setPrice(Double.parseDouble(addpartpricein.getText()));
                    partnew.setStock(Integer.parseInt(addpartinvin.getText()));
                    partnew.setMin(Integer.parseInt(addpartminin.getText()));
                    partnew.setMax(Integer.parseInt(addpartmaxin.getText()));
                    ((InHouse) partnew).setMachineID(Integer.parseInt(addpartmachineidin.getText()));

                    Inventory.addPart(partnew);
                    System.out.println("inhouse");


                }
                else {

                    partnew = new Outsourced(id, "", 0.0, 0, 0, 0, "");
                    partnew.setName(addpartnamein.getText());
                    partnew.setPrice(Double.parseDouble(addpartpricein.getText()));
                    partnew.setStock(Integer.parseInt(addpartinvin.getText()));
                    partnew.setMin(Integer.parseInt(addpartminin.getText()));
                    partnew.setMax(Integer.parseInt(addpartmaxin.getText()));
                    ((Outsourced) partnew).setCompanyName(addpartmachineidin.getText());

                    Inventory.addPart(partnew);
                    System.out.println("outsourced");

                }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}

