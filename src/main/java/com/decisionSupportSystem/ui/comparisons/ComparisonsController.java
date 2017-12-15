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
import main.java.com.decisionSupportSystem.logic.DataModel;
import sun.reflect.generics.tree.Tree;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ComparisonsController implements Initializable {
    @FXML private TreeView criteriasList;

    @FXML private Button addCriteria;
    @FXML private Button addSubcriteria;
    @FXML private Button delete;

    private DataModel dataModel = new DataModel();

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

                dataModel.getCriterias().add(criteria);
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
                System.out.println(treeItemParentCriteria.toString());
                Criteria parentCriteria = treeItemParentCriteria.getValue();
                System.out.println(parentCriteria.toString());
                subcriteria.setParent(parentCriteria);

                // Add subcriteria to DataModel
                if(dataModel.getSubCriterias().get(parentCriteria) == null) {
                    ArrayList<Criteria> subcriteriasAL = new ArrayList<>();
                    subcriteriasAL.add(subcriteria);
                    dataModel.getSubCriterias().put(parentCriteria, subcriteriasAL);
                } else {
                    dataModel.getSubCriterias().get(parentCriteria).add(subcriteria);
                }

                // Add subcriteria to TreeView
                TreeItem<Criteria> subcriteriaTreeView = new TreeItem<>();
                subcriteriaTreeView.setValue(subcriteria);
                treeItemParentCriteria.getChildren().add(subcriteriaTreeView);

            }
        });
    }
}
