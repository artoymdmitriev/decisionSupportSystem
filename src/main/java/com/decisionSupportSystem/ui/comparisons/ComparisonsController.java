package main.java.com.decisionSupportSystem.ui.comparisons;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.com.decisionSupportSystem.logic.Criteria;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ComparisonsController implements Initializable {
    @FXML private TreeView criteriasList;

    @FXML private Button addCriteria;
    @FXML private Button addSubcriteria;
    @FXML private Button delete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<Criteria> rootItem = new TreeItem<>(new Criteria("Критерии"));
        criteriasList.setRoot(rootItem);
        addCriteria.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("input.fxml"));
                Scene newScene;
                try {
                    newScene = new Scene(loader.load());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // TODO: handle error
                    return;
                }

                Stage inputStage = new Stage();
                inputStage.initOwner(addCriteria.getScene().getWindow());
                inputStage.setScene(newScene);
                inputStage.showAndWait();

                Criteria criteria = new Criteria(loader.<InputController>getController().getFieldValue());
                TreeItem<Criteria> criteriaTreeItem = new TreeItem<>(criteria);
                rootItem.getChildren().add(criteriaTreeItem);
            }
        });

        addSubcriteria.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("input.fxml"));
                Scene newScene;
                try {
                    newScene = new Scene(loader.load());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // TODO: handle error
                    return;
                }

                Stage inputStage = new Stage();
                inputStage.initOwner(addCriteria.getScene().getWindow());
                inputStage.setScene(newScene);
                inputStage.showAndWait();
            }
        });
    }
}
