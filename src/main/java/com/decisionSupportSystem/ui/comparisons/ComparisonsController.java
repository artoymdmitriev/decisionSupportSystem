package main.java.com.decisionSupportSystem.ui.comparisons;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.com.decisionSupportSystem.logic.Criteria;
import main.java.com.decisionSupportSystem.logic.DataModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ComparisonsController implements Initializable, Observer {
    @FXML private TreeView criteriasList;

    @FXML private Button addCriteria;
    @FXML private Button addSubcriteria;
    @FXML private Button delete;
    @FXML private Button addAlternatives;

    @FXML private VBox rightPane;

    private DataModel dataModel = new DataModel();
    private int numberOfAlternatives = 0;
    private int numberOfCriterias = 0;
    private int numberOfSubcriterias = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set controller as an observer of datamodel
        dataModel.addObserver(this);

        TreeItem<Criteria> rootItem = new TreeItem<>(new Criteria("Критерии", null));
        rootItem.setExpanded(true);

        //set listener for the list of criterias
        criteriasList.setRoot(rootItem);
        criteriasList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            // create a table for comparison
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                rightPane.getChildren().clear();
                TreeItem<Criteria> selectedItem = (TreeItem<Criteria>) newValue;
                if(selectedItem.getValue().equals(rootItem.getValue())) {
                    // rate criterias
                    setRatesForCriterias(selectedItem);
                } else if(!selectedItem.getChildren().isEmpty()) {
                    // rate subcriterias
                    setRatesForSubcriterias(selectedItem);
                } else if(selectedItem.getChildren().isEmpty()) {
                    // rate alternatives
                    setRatesForAlternatives(selectedItem);
                }
            }
        });

        addCriteria.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Criteria criteria = new Criteria(getInputText(), rootItem.getValue());
                dataModel.addCriteria(criteria);
            }
        });

        addSubcriteria.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Create subcriteria
                TreeItem<Criteria> treeItemParentCriteria = (TreeItem<Criteria>) criteriasList.getFocusModel().getFocusedItem();
                Criteria parentCriteria = treeItemParentCriteria.getValue();
                Criteria subcriteria = new Criteria(getInputText(), parentCriteria);

                // Add subcriteria to DataModel
                if(dataModel.getSubCriterias().get(parentCriteria) == null) {
                    ArrayList<Criteria> subcriteriasAL = new ArrayList<>();
                    subcriteriasAL.add(subcriteria);
                    dataModel.addSubcriteria(parentCriteria, subcriteriasAL);
                } else {
                    dataModel.addSubcriteria(parentCriteria, subcriteria);
                }
            }
        });

        addAlternatives.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Set up the number of alternatives
                numberOfAlternatives = Integer.parseInt(getInputText());
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        criteriasList.getRoot().getChildren().clear();
        // add all criterias
        for(Criteria cr : dataModel.getCriterias()) {
            TreeItem<Criteria> criteriaTreeItem = new TreeItem<>(cr);
            criteriasList.getRoot().getChildren().add(criteriaTreeItem);
        }

        // add all subcriterias
        for(Map.Entry<Criteria, ArrayList<Criteria>> entry : dataModel.getSubCriterias().entrySet()) {
            Criteria parentCriteria = entry.getKey();
            for(Object item : criteriasList.getRoot().getChildren()) {
                Criteria c = ((TreeItem<Criteria>) item).getValue();
                if(parentCriteria.getName().equals(c.getName())) {
                    for(int i = 0; i < entry.getValue().size(); i++) {
                        ((TreeItem<Criteria>) item).getChildren().add(new TreeItem<>(entry.getValue().get(i)));
                    }
                }
            }
        }
    }

    private void setRatesForCriterias(TreeItem<Criteria> selectedItem) {
        // get number of criterias
        numberOfCriterias = selectedItem.getChildren().size();

        // create an array of textfields
        TextField textFields[][] = new TextField[numberOfCriterias][numberOfCriterias];
        for(int i = 0; i < numberOfCriterias; i++) {
            for(int j = 0; j < numberOfCriterias; j++) {
                textFields[i][j] = new TextField();

                // set 1 for dioganal fields
                if(i == j) {
                    textFields[i][j].setText("1.0");
                    textFields[i][j].setEditable(false);
                }
            }
        }

        addDioganalValuesChangeListener(textFields);

        GridPane gridPane = createAndFillGridPane(textFields, numberOfCriterias, numberOfCriterias);
        rightPane.getChildren().add(gridPane);

        Button saveData = createSaveButton(selectedItem, gridPane, numberOfCriterias, numberOfCriterias);
        rightPane.getChildren().add(saveData);
    }

    private void setRatesForSubcriterias(TreeItem<Criteria> selectedItem) {
        // find the number of subcriterias
        numberOfSubcriterias = dataModel.getSubCriterias().get(selectedItem.getValue()).size();

        TextField textFields[][] = new TextField[numberOfSubcriterias][numberOfSubcriterias];
        for(int i = 0; i < numberOfSubcriterias; i++) {
            for(int j = 0; j < numberOfSubcriterias; j++) {
                textFields[i][j] = new TextField();

                // set 1 for dioganal fields
                if(i == j) {
                    textFields[i][j].setText("1.0");
                    textFields[i][j].setEditable(false);
                }
            }
        }

        addDioganalValuesChangeListener(textFields);

        GridPane gridPane = createAndFillGridPane(textFields, numberOfSubcriterias, numberOfSubcriterias);
        rightPane.getChildren().add(gridPane);

        Button saveData = createSaveButton(selectedItem, gridPane, numberOfSubcriterias, numberOfSubcriterias);
        rightPane.getChildren().add(saveData);
    }

    private void setRatesForAlternatives(TreeItem<Criteria> selectedItem) {
        // create an array of textfields
        TextField textFields[][] = new TextField[numberOfAlternatives][numberOfAlternatives];
        for(int i = 0; i < numberOfAlternatives; i++) {
            for(int j = 0; j < numberOfAlternatives; j++) {
                textFields[i][j] = new TextField();

                // set 1 for dioganal fields
                if(i == j) {
                    textFields[i][j].setText("1.0");
                    textFields[i][j].setEditable(false);
                }
            }
        }

        addDioganalValuesChangeListener(textFields);

        GridPane gridPane = createAndFillGridPane(textFields, numberOfAlternatives, numberOfAlternatives);
        rightPane.getChildren().add(gridPane);

        Button saveData = createSaveButton(selectedItem, gridPane, numberOfAlternatives, numberOfAlternatives);
        rightPane.getChildren().add(saveData);
    }

    private String getInputText() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("input.fxml"));
        Scene newScene = null;
        try {
            newScene = new Scene(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Stage inputStage = new Stage();
        inputStage.initOwner(addCriteria.getScene().getWindow());
        inputStage.setScene(newScene);
        inputStage.showAndWait();

        String result = loader.<InputController>getController().getFieldValue();

        return result;
    }

    private void addDioganalValuesChangeListener(TextField[][] textFields) {
        // change dioganal value in table
        for(int i = 0; i < textFields.length; i++) {
            for(int j = 0; j < textFields[i].length; j++) {
                textFields[i][j].focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                        if(!newPropertyValue) {
                            for(int ii = 0; ii < textFields.length; ii++) {
                                for(int jj = 0; jj < textFields[ii].length; jj++) {
                                    if(textFields[ii][jj].focusedProperty().equals(arg0)) {
                                        String valueStr = textFields[ii][jj].getText();
                                        if(!valueStr.isEmpty()) {
                                            double valueDouble = Double.parseDouble(valueStr);
                                            double diagValue = Math.round((1.0 / valueDouble) * 100.0) / 100.0;
                                            textFields[jj][ii].setText(String.valueOf(diagValue));
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    public GridPane createAndFillGridPane(TextField[][] textFields, int rows, int columns) {
        // create pane for textfields
        GridPane gridPane = new GridPane();

        // create constraints for gridPane
        for(int i = 0; i < rows; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / rows);
            gridPane.getColumnConstraints().add(colConst);
        }

        for(int i = 0; i < columns; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / columns);
            gridPane.getRowConstraints().add(rowConst);
        }

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                gridPane.add(textFields[i][j], i, j);
            }
        }

        return gridPane;
    }

    private Button createSaveButton(TreeItem<Criteria> selectedItem, GridPane gridPane, int rows, int columns) {
        Button saveData = new Button();
        saveData.setText("Сохранить");
        saveData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Criteria cr = selectedItem.getValue();

                // delete old alternatives rates if they exist
                if(dataModel.getAlternativesRates().get(cr) != null) {
                    dataModel.getAlternativesRates().remove(cr);
                }

                // load values from user
                double[][] rates = new double[rows][columns];
                for(int i = 0; i < rows; i++) {
                    for(int j = 0; j < columns; j++) {
                        for(Node node : gridPane.getChildren()) {
                            if(gridPane.getRowIndex(node) == i && gridPane.getColumnIndex(node) == j) {
                                rates[i][j] = Integer.parseInt(((TextField) node).getText());
                            }
                        }
                    }
                }

                dataModel.getAlternativesRates().put(cr, rates);
            }
        });

        return saveData;
    }
}
