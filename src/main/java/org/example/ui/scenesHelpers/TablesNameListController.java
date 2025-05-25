package org.example.ui.scenesHelpers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.repository.DBManager;
import org.example.repository.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TablesNameListController {
    private static final Logger logger = LoggerFactory.getLogger(TablesNameListController.class);
    private DBManager dbManager = new DBManager();

    public void loadTablesName(VBox tablesNameContainer) {
        int tablesSize = 0;
        tablesNameContainer.getChildren().clear();

        SceneController sceneController = new SceneController();
        try (Connection conn = DatabaseConnector.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");

                HBox row = gethBox(tablesNameContainer, tableName, sceneController);

                tablesNameContainer.getChildren().add(row);
                tablesSize++;
            }
            tablesNameContainer.autosize();
            logger.info("Loaded {} table names from DB", tablesSize);

        } catch (SQLException e) {
            logger.error("Failed to load table name from db");
        }
    }

    HBox gethBox(VBox tablesNameContainer, String tableName, SceneController sceneController) {
        Hyperlink tableLink = new Hyperlink(tableName);
        tableLink.setMaxWidth(Double.MAX_VALUE);
        tableLink.setOnAction(event -> sceneController.goToRecordCollectionScene(event, tableName));

        Button deleteButton = new Button("🗑");
        deleteButton.setOnAction(event -> {
            dbManager.deleteTable(tableName);
            loadTablesName(tablesNameContainer); // оновити список
        });
        HBox row = new HBox(10, deleteButton, tableLink);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    public void searchAndShowTables(String searchQuery, VBox tablesNameContainer) {
        SceneController sceneController = new SceneController();

        try (Connection conn = DatabaseConnector.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                if (tableName.toLowerCase().contains(searchQuery.toLowerCase())) {
                    HBox row = gethBox(tablesNameContainer, tableName, sceneController);
                    tablesNameContainer.getChildren().add(row);
                    logger.error("Collection with name like {} was found", searchQuery);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to search collection {}", searchQuery);
        }
    }

}
