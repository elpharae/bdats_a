package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VyrobniProcesGUI extends Application {

    static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        VyrobniProcesGUI.primaryStage = stage;

        Parent root = FXMLLoader.load(getClass().getResource("VyrobniProcesGUIFXML.fxml"));

        stage.setTitle("Semestrální práce A - Výrobní procesy");
        stage.setScene(new Scene(root, 600, 600));
        stage.setResizable(false);

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}