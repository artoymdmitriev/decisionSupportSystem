package main.java.com.decisionSupportSystem.ui.comparisons;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.java.com.decisionSupportSystem.logic.Alternative;
import main.java.com.decisionSupportSystem.logic.DataModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AlternativesController implements Initializable{
    private DataModel dataModel;

    @FXML private ListView alternativesList;
    @FXML private TextField valueField;
    @FXML private Button addButton;
    @FXML private Button submitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!valueField.getText().equals("")) {
                    Alternative alternative = new Alternative(valueField.getText());
                    dataModel.getAlternatives().add(alternative);

                    alternativesList.getItems().clear();
                    alternativesList.getItems().addAll(dataModel.getAlternatives());
                    valueField.clear();
                }
            }
        });

        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                alternativesList.getScene().getWindow().hide();
            }
        });
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
        alternativesList.getItems().addAll(dataModel.getAlternatives());
    }
}
