package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Javadoc folder located in Shamika Inventory folder.
/**FUTURE ENHANCEMENT will be able to display the Company Name and Machine ID on the main screen. This class creates an app that displays message. */
public class Main extends Application {


    /**Creates the stage and loads initial scene.*/
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/main_screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    /** @param args the command line arguments.*/
    public static void main(String[] args) {



        launch(args);

    }
}
