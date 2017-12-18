package main.java.com.decisionSupportSystem.ui.comparisons;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.com.decisionSupportSystem.logic.Alternative;
import main.java.com.decisionSupportSystem.logic.AnalyticHierarchyProcess;
import main.java.com.decisionSupportSystem.logic.Criteria;
import main.java.com.decisionSupportSystem.logic.DataModel;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class ComparisonsController implements Initializable, Observer {
    @FXML
    private TreeView criteriasList;

    @FXML
    private Button addCriteria;
    @FXML
    private Button addSubcriteria;
    @FXML
    private Button addAlternatives;
    @FXML
    private Button getVector;

    @FXML
    private VBox rightPane;

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
                if (selectedItem.getValue().equals(rootItem.getValue())) {
                    // rate criterias
                    setRatesForCriterias(selectedItem);
                } else if (!selectedItem.getChildren().isEmpty()) {
                    // rate subcriterias
                    setRatesForSubcriterias(selectedItem);
                } else if (selectedItem.getChildren().isEmpty()) {
                    // rate alternatives
                    setRatesForAlternatives(selectedItem);
                }
            }
        });

        addCriteria.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String subcriteriaName = getInputText();
                if(!subcriteriaName.equals("")) {
                    Criteria criteria = new Criteria(subcriteriaName, rootItem.getValue());
                    // Add subcriteria to DataModel
                    dataModel.addCriteria(criteria);
                }
            }
        });

        addSubcriteria.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Create subcriteria
                TreeItem<Criteria> treeItemParentCriteria = (TreeItem<Criteria>) criteriasList.getFocusModel().getFocusedItem();
                Criteria parentCriteria = treeItemParentCriteria.getValue();

                String subcriteriaName = getInputText();
                if(!subcriteriaName.equals("")) {
                    Criteria subcriteria = new Criteria(subcriteriaName, parentCriteria);
                    // Add subcriteria to DataModel
                    dataModel.addSubcriteria(parentCriteria, subcriteria);
                }
            }
        });

        addAlternatives.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                ArrayList<Alternative> alternatives = inputAlternatives();
//                numberOfAlternatives = alternatives.size();
//                dataModel.setAlternatives(alternatives);
                inputAlternatives();
            }
        });

        getVector.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AnalyticHierarchyProcess ahp = new AnalyticHierarchyProcess(dataModel);
                HashMap<Alternative, Double> vector = ahp.getVector();

                // create message text
                String text = "";
                for(Map.Entry<Alternative, Double> entry : vector.entrySet()) {
                    text += entry.getKey().getName() + ": " + entry.getValue() + '\n';
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Вектор результатов");
                alert.setHeaderText(null);
                alert.setContentText(text);

                alert.showAndWait();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        criteriasList.getRoot().getChildren().clear();
        // add all criterias
        for (Criteria cr : dataModel.getCriterias()) {
            TreeItem<Criteria> criteriaTreeItem = new TreeItem<>(cr);
            criteriasList.getRoot().getChildren().add(criteriaTreeItem);
        }

        // add all subcriterias
        for (Map.Entry<Criteria, ArrayList<Criteria>> entry : dataModel.getSubCriterias().entrySet()) {
            Criteria parentCriteria = entry.getKey();
            for (Object item : criteriasList.getRoot().getChildren()) {
                Criteria c = ((TreeItem<Criteria>) item).getValue();
                if (parentCriteria.equals(c)) {
                    for (int i = 0; i < entry.getValue().size(); i++) {
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
        for (int i = 0; i < numberOfCriterias; i++) {
            for (int j = 0; j < numberOfCriterias; j++) {
                textFields[i][j] = new TextField();

                // set 1 for dioganal fields
                if (i == j) {
                    textFields[i][j].setText("1.0");
                    textFields[i][j].setEditable(false);
                }
            }
        }

        addDioganalValuesChangeListener(textFields);

        GridPane gridPane = createAndFillGridPane(textFields, numberOfCriterias, numberOfCriterias);
        rightPane.getChildren().add(gridPane);

        Button saveData = new Button();
        saveData.setText("Сохранить");
        saveData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // delete old alternatives rates if they exist
                // load values from user
                double[][] rates = new double[numberOfCriterias][numberOfCriterias];
                for (int i = 0; i < numberOfCriterias; i++) {
                    for (int j = 0; j < numberOfCriterias; j++) {
                        for (Node node : gridPane.getChildren()) {
                            if (gridPane.getRowIndex(node) == i && gridPane.getColumnIndex(node) == j) {
                                rates[i][j] = Double.parseDouble(((TextField) node).getText());
                            }
                        }
                    }
                }

                dataModel.setCriteriasRates(rates);
            }
        });
        rightPane.getChildren().add(saveData);
    }

    private void setRatesForSubcriterias(TreeItem<Criteria> selectedItem) {
        // find the number of subcriterias
        numberOfSubcriterias = dataModel.getSubCriterias().get(selectedItem.getValue()).size();

        TextField textFields[][] = new TextField[numberOfSubcriterias][numberOfSubcriterias];
        for (int i = 0; i < numberOfSubcriterias; i++) {
            for (int j = 0; j < numberOfSubcriterias; j++) {
                textFields[i][j] = new TextField();

                // set 1 for dioganal fields
                if (i == j) {
                    textFields[i][j].setText("1.0");
                    textFields[i][j].setEditable(false);
                }
            }
        }

        addDioganalValuesChangeListener(textFields);

        GridPane gridPane = createAndFillGridPane(textFields, numberOfSubcriterias, numberOfSubcriterias);
        rightPane.getChildren().add(gridPane);

        Button saveData = new Button();
        saveData.setText("Сохранить");
        saveData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Criteria cr = selectedItem.getValue();

                // delete old alternatives rates if they exist
                if (dataModel.getAlternativesRates().get(cr) != null) {
                    dataModel.getAlternativesRates().remove(cr);
                }

                // load values from user
                double[][] rates = new double[numberOfSubcriterias][numberOfSubcriterias];
                for (int i = 0; i < numberOfSubcriterias; i++) {
                    for (int j = 0; j < numberOfSubcriterias; j++) {
                        for (Node node : gridPane.getChildren()) {
                            if (gridPane.getRowIndex(node) == i && gridPane.getColumnIndex(node) == j) {
                                rates[i][j] = Double.parseDouble(((TextField) node).getText());
                            }
                        }
                    }
                }

                dataModel.getSubCriteriasRates().put(cr, rates);
            }
        });
        rightPane.getChildren().add(saveData);
    }

    private void setRatesForAlternatives(TreeItem<Criteria> selectedItem) {
        // create an array of textfields
        TextField textFields[][] = new TextField[numberOfAlternatives][numberOfAlternatives];
        for (int i = 0; i < numberOfAlternatives; i++) {
            for (int j = 0; j < numberOfAlternatives; j++) {
                textFields[i][j] = new TextField();

                // set 1 for dioganal fields
                if (i == j) {
                    textFields[i][j].setText("1.0");
                    textFields[i][j].setEditable(false);
                }
            }
        }

        addDioganalValuesChangeListener(textFields);

        GridPane gridPane = createAndFillGridPane(textFields, numberOfAlternatives, numberOfAlternatives);
        rightPane.getChildren().add(gridPane);

        Button saveData = new Button();
        saveData.setText("Сохранить");
        saveData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Criteria cr = selectedItem.getValue();

                // delete old alternatives rates if they exist
                if (dataModel.getAlternativesRates().get(cr) != null) {
                    dataModel.getAlternativesRates().remove(cr);
                }

                // load values from user
                double[][] rates = new double[numberOfAlternatives][numberOfAlternatives];
                for (int i = 0; i < numberOfAlternatives; i++) {
                    for (int j = 0; j < numberOfAlternatives; j++) {
                        for (Node node : gridPane.getChildren()) {
                            if (gridPane.getRowIndex(node) == i && gridPane.getColumnIndex(node) == j) {
                                rates[i][j] = Double.parseDouble(((TextField) node).getText());
                            }
                        }
                    }
                }

                dataModel.getAlternativesRates().put(cr, rates);
            }
        });
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
        for (int i = 0; i < textFields.length; i++) {
            for (int j = 0; j < textFields[i].length; j++) {
                textFields[i][j].focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                        if (!newPropertyValue) {
                            for (int ii = 0; ii < textFields.length; ii++) {
                                for (int jj = 0; jj < textFields[ii].length; jj++) {
                                    if (textFields[ii][jj].focusedProperty().equals(arg0)) {
                                        String valueStr = textFields[ii][jj].getText();
                                        if (!valueStr.isEmpty()) {
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
        for (int i = 0; i < rows; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / rows);
            gridPane.getColumnConstraints().add(colConst);
        }

        for (int i = 0; i < columns; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / columns);
            gridPane.getRowConstraints().add(rowConst);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gridPane.add(textFields[i][j], i, j);
            }
        }

        return gridPane;
    }

    private void inputAlternatives() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("alternatives.fxml"));
        Scene newScene = null;
        try {
            newScene = new Scene(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        AlternativesController ac = (AlternativesController) loader.getController();
        ac.setDataModel(dataModel);

        Stage inputStage = new Stage();
        inputStage.initOwner(addCriteria.getScene().getWindow());
        inputStage.setScene(newScene);
        inputStage.showAndWait();

        //ArrayList<Alternative> alternativesList = loader.<AlternativesController>getController().getAlternatives();
        //return alternativesList;
    }
}