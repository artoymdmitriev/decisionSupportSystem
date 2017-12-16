package main.java.com.decisionSupportSystem.ui.comparisons;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class InputController implements Initializable{
    @FXML private TextField valueField;
    @FXML private Button submitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                valueField.getScene().getWindow().hide();
            }
        });
    }

    public String getFieldValue() {
        return valueField.getText();
    }
}
