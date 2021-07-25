package View_Controller;


import Model.*;
import Model.Part;
import Model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/** This class is the controller for the modify part screen.*/
public class modifypartscreencontroller implements Initializable {

    private ObservableList<Part> parts = FXCollections.observableArrayList();

    Parent scene;
    Stage stage;
    private int index;
    private Part selectedPart;


    @FXML
    private AnchorPane addpartscreen;

    @FXML
    private ToggleGroup modifyparttoggle;

    /** This label will change to Machine ID or Company Name. */
    @FXML
    private Label machineorcompanylabel;

    @FXML
    private RadioButton inhouseradio;
    @FXML
    private RadioButton outsourcedradio;
    @FXML
    private TextField modifypartid;
    @FXML
    private TextField modifypartnamein;

    @FXML
    private TextField modifypartinvin;

    @FXML
    private TextField modifypartpricein;

    @FXML
    private TextField modifypartmaxin;

    @FXML
    private TextField modifypartmachineidin;

    @FXML
    private TextField modifypartminin;




    /** This button will change the label to Company Name. */
    @FXML
   public void Outsourcedscreen(ActionEvent event) {

        machineorcompanylabel.setText("Company Name");

    }

    /** This button will change the label to Machine ID. */
    @FXML
    public void inhousescreen(ActionEvent event) {

        machineorcompanylabel.setText("Machine ID");

    }

    /** This button returns to Main Screen. */
    @FXML
    public void modifypartcancelhandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/main_screen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This will get the selected part from the main screen,
     * save the update part as new, and delete the old one.
     *
     */
    @FXML
    public void modifypartsavehander(ActionEvent event) throws IOException {

        String name = modifypartnamein.getText();
        int stock = Integer.parseInt(modifypartinvin.getText());
        double price = Double.parseDouble(modifypartpricein.getText());
        int min = Integer.parseInt(modifypartminin.getText());
        int max = Integer.parseInt(modifypartmaxin.getText());


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


            if (modifyparttoggle.getSelectedToggle().equals(inhouseradio)) {
                Part modParts = new InHouse(Integer.parseInt(modifypartid.getText()),
                        modifypartnamein.getText(),
                        Double.parseDouble(modifypartpricein.getText()),
                        Integer.parseInt(modifypartinvin.getText()),
                        Integer.parseInt(modifypartminin.getText()),
                        Integer.parseInt(modifypartmaxin.getText()),
                        Integer.parseInt(modifypartmachineidin.getText()));

                Inventory.addPart(modParts);
                Inventory.deletePart(selectedPart);
            }

            if (modifyparttoggle.getSelectedToggle().equals(outsourcedradio)) {
                Part modParts = new Outsourced(Integer.parseInt(modifypartid.getText()),
                        modifypartnamein.getText(),
                        Double.parseDouble(modifypartpricein.getText()),
                        Integer.parseInt(modifypartinvin.getText()),
                        Integer.parseInt(modifypartminin.getText()),
                        Integer.parseInt(modifypartmaxin.getText()),
                        modifypartmachineidin.getText());

                Inventory.addPart(modParts);
                Inventory.deletePart(selectedPart);

            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/main_screen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }


   /**This will see if the part in inhouse or outsourced*/
        public void getSelectedPart(Part modpart) {
        selectedPart = modpart;

        modifypartid.setText(String.valueOf(selectedPart.getId()));
        modifypartnamein.setText(selectedPart.getName());
        modifypartinvin.setText(String.valueOf(selectedPart.getStock()));
        modifypartpricein.setText(String.valueOf(selectedPart.getPrice()));
        modifypartmaxin.setText(String.valueOf(selectedPart.getMax()));
        modifypartminin.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHouse) {
            inhouseradio.setSelected(true);
            machineorcompanylabel.setText("Machine ID");
            modifypartmachineidin.setText(String.valueOf(((InHouse)selectedPart).getMachineID()));


        }

        if (selectedPart instanceof Outsourced) {
            outsourcedradio.setSelected(true);
            machineorcompanylabel.setText("Company Name");
            modifypartmachineidin.setText(((Outsourced) selectedPart).getCompanyName());

        }




    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {






    }





}
