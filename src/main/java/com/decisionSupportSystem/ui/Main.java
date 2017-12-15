package main.java.com.decisionSupportSystem.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.com.decisionSupportSystem.ui.comparisons.ComparisonsController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("comparisons/comparisons.fxml"));
        root.setCenter(loader.load());
        ComparisonsController comparisonsController = loader.getController();

        primaryStage.setTitle("DSS");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
