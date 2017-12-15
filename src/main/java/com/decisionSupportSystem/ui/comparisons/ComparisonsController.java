package main.java.com.decisionSupportSystem.ui.comparisons;

import com.sun.rowset.internal.Row;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private int numberOfAlternatives = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataModel.addObserver(this);

        TreeItem<Criteria> rootItem = new TreeItem<>(new Criteria("Критерии"));
        rootItem.setExpanded(true);
        criteriasList.setRoot(rootItem);
        criteriasList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            // creating a table for comparison
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<Criteria> selectedItem = (TreeItem<Criteria>) newValue;
                if(selectedItem.getChildren().isEmpty()) {
                    // create an array of textfields
                    TextField textFields[][] = new TextField[numberOfAlternatives][numberOfAlternatives];
                    for(int i = 0; i < numberOfAlternatives; i++) {
                        for(int j = 0; j < numberOfAlternatives; j++) {
                            textFields[i][j] = new TextField();
                        }
                    }

                    // crate pane for textfields
                    GridPane gridPane = new GridPane();

                    // create constraints for gridPane
                    for(int i = 0; i < numberOfAlternatives; i++) {
                        ColumnConstraints colConst = new ColumnConstraints();
                        colConst.setPercentWidth(100.0 / numberOfAlternatives);
                        gridPane.getColumnConstraints().add(colConst);
                    }

                    System.out.println("blebleble");
                    for(int i = 0; i < numberOfAlternatives; i++) {
                        RowConstraints rowConst = new RowConstraints();
                        rowConst.setPercentHeight(100.0 / numberOfAlternatives);
                        gridPane.getRowConstraints().add(rowConst);
                    }

                    for(int i = 0; i < numberOfAlternatives; i++) {
                        for(int j = 0; j < numberOfAlternatives; j++) {
                            gridPane.add(textFields[i][j], i, j);
                        }
                    }

                    rightPane.getChildren().add(gridPane);

                }
            }
        });

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
                dataModel.addCriteria(criteria);
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

                // Create subcriteria
                Criteria subcriteria = new Criteria(loader.<InputController>getController().getFieldValue());
                TreeItem<Criteria> treeItemParentCriteria = (TreeItem<Criteria>) criteriasList.getFocusModel().getFocusedItem();
                Criteria parentCriteria = treeItemParentCriteria.getValue();
                subcriteria.setParent(parentCriteria);

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

                numberOfAlternatives = Integer.parseInt(loader.<InputController>getController().getFieldValue());
                System.out.println(numberOfAlternatives);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Hola!");
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
}
