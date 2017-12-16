package main.java.com.decisionSupportSystem.ui.comparisons;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.java.com.decisionSupportSystem.logic.Alternative;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AlternativesController implements Initializable{
    private ArrayList<Alternative> alternatives = new ArrayList<>();

    @FXML private ListView alternativesList;
    @FXML private TextField valueField;
    @FXML private Button addButton;
    @FXML private Button submitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alternative alternative = new Alternative(valueField.getText());
                alternatives.add(alternative);

                alternativesList.getItems().clear();
                alternativesList.getItems().addAll(alternatives);
            }
        });

        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                alternativesList.getScene().getWindow().hide();
            }
        });
    }

    public ArrayList<Alternative> getAlternatives() {
        return alternatives;
    }
}
