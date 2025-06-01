package org.example.ui.scenesHelpers;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.repository.DatabaseConnector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CollectionListControllerTest {

    private CollectionListController controller;
    private VBox vbox;

    @BeforeAll
    public static void initToolkit() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown); // запускає JavaFX thread
        latch.await();
    }

    @BeforeEach
    public void setup() {
        controller = new CollectionListController();
        vbox = new VBox();
    }

    @Test
    public void testLoadCollectionsName() throws Exception {
        try (
                MockedStatic<DatabaseConnector> mockedStatic = Mockito.mockStatic(DatabaseConnector.class)
        ) {
            // Моки бази
            Connection mockConn = mock(Connection.class);
            DatabaseMetaData metaData = mock(DatabaseMetaData.class);
            ResultSet resultSet = mock(ResultSet.class);

            // Повертає одне ім'я таблиці
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getString("TABLE_NAME")).thenReturn("TestTable");
            when(metaData.getTables(null, null, "%", new String[]{"TABLE"})).thenReturn(resultSet);
            when(mockConn.getMetaData()).thenReturn(metaData);

            mockedStatic.when(DatabaseConnector::getConnection).thenReturn(mockConn);

            controller.loadCollectionsName(vbox);

            assertEquals(0, vbox.getChildren().size());
            HBox row = (HBox) vbox.getChildren().getFirst();
            assertEquals("🗑", ((javafx.scene.control.Button) row.getChildren().getFirst()).getText());
        }
    }

    @Test
    public void testSearchAndShowTables() throws Exception {
        try (
                MockedStatic<DatabaseConnector> mockedStatic = Mockito.mockStatic(DatabaseConnector.class)
        ) {
            Connection mockConn = mock(Connection.class);
            DatabaseMetaData metaData = mock(DatabaseMetaData.class);
            ResultSet resultSet = mock(ResultSet.class);

            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getString("TABLE_NAME")).thenReturn("UsersTable");
            when(metaData.getTables(null, null, "%", new String[]{"TABLE"})).thenReturn(resultSet);
            when(mockConn.getMetaData()).thenReturn(metaData);

            mockedStatic.when(DatabaseConnector::getConnection).thenReturn(mockConn);

            controller.searchAndShowTables("users", vbox);

            assertEquals(0, vbox.getChildren().size());
        }
    }
}
