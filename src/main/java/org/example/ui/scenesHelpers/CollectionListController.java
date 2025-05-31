package org.example.ui.scenesHelpers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.models.Collection;
import org.example.repository.DBCollectionManager;
import org.example.repository.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectionListController {
    private static final Logger logger = LoggerFactory.getLogger(CollectionListController.class);
    private DBCollectionManager collectionManager = new DBCollectionManager();

    public void loadCollectionsName(VBox collectionsNameContainer) {
        int tablesSize = 0;
        collectionsNameContainer.getChildren().clear();
        SceneController sceneController = new SceneController();
        for(Collection collection : collectionManager.getAllCollections()){
            HBox row = gethBox(collectionsNameContainer, collection, sceneController);
            collectionsNameContainer.getChildren().add(row);
            tablesSize++;
        }
        collectionsNameContainer.autosize();
        logger.info("Loaded {} table names from DB", tablesSize);
    }

    HBox gethBox(VBox tablesNameContainer, Collection collection, SceneController sceneController) {
        Hyperlink tableLink = new Hyperlink(collection.getName());
        tableLink.setMaxWidth(Double.MAX_VALUE);
        tableLink.setOnAction(event -> sceneController.goToRecordCollectionScene(event, collection.getId()));

        Button deleteButton = new Button("🗑");
        deleteButton.setOnAction(event -> {
            collectionManager.deleteCollection(collection.getId());
            loadCollectionsName(tablesNameContainer); // оновити список
        });
        HBox row = new HBox(10, deleteButton, tableLink);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    public void searchAndShowTables(String searchQuery, VBox tablesNameContainer) {
        SceneController sceneController = new SceneController();

        for(Collection collection : collectionManager.getAllCollections()) {
            if (collection.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                HBox row = gethBox(tablesNameContainer, collection, sceneController);
                tablesNameContainer.getChildren().add(row);
                logger.info("Collection with name like {} was found", searchQuery);
            }
        }

    }

}
