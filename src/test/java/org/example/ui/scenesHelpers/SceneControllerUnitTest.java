package org.example.ui.scenesHelpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ui.scenes.CollectionSceneController;
import org.example.ui.scenes.CreateRecordSceneController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SceneControllerUnitTest {

    private SceneController controller;

    @BeforeEach
    void setup() {
        controller = spy(new SceneController() {
            @Override
            protected URL getFXMLResource(String path) {
                try {
                    return new URL("file:/dummy");
                } catch (Exception e) {
                    return null;
                }
            }
        });
    }

    private ActionEvent mockEvent(Stage stage) {
        ActionEvent event = mock(ActionEvent.class);
        Node node = mock(Node.class);
        Scene scene = mock(Scene.class);

        when(event.getSource()).thenReturn(node);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWindow()).thenReturn(stage);

        return event;
    }

    private Parent mockParentWithStyleClass() {
        Parent root = mock(Parent.class);
        ObservableList<String> styleClass = FXCollections.observableArrayList();
        when(root.getStyleClass()).thenReturn(styleClass);
        return root;
    }

//    @Test
//    void testGoBackToMainScene_success() throws Exception {
//        Stage stage = mock(Stage.class);
//        ActionEvent event = mockEvent(stage);
//        Parent root = mockParentWithStyleClass();
//
//        try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
//                (loader, context) -> when(loader.load()).thenReturn(root))) {
//
//            controller.goBackToMainScene(event);
//
//            verify(stage).setScene(any(Scene.class));
//            verify(stage).show();
//        }
//    }

    @Test
    void testGoBackToMainScene_loadFails_throwsRuntimeException() {
        Stage stage = mock(Stage.class);
        ActionEvent event = mockEvent(stage);

        try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                (loader, context) -> when(loader.load()).thenThrow(new IOException("Fail")))) {

            assertThrows(RuntimeException.class, () -> controller.goBackToMainScene(event));
        }
    }

    @Test
    void testGoToRecordCollectionScene_success() throws Exception {
        Stage stage = mock(Stage.class);
        ActionEvent event = mockEvent(stage);
        Parent root = mockParentWithStyleClass();
        CollectionSceneController mockSceneController = mock(CollectionSceneController.class);

        try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                (loader, context) -> {
                    when(loader.load()).thenReturn(root);
                    when(loader.getController()).thenReturn(mockSceneController);
                })) {

            controller.goToRecordCollectionScene(event, 123);

            verify(mockSceneController).initialize(123);
            verify(stage).setScene(any(Scene.class));
            verify(stage).show();
        }
    }

    @Test
    void testGoToRecordCollectionScene_loadFails_throwsRuntimeException() {
        Stage stage = mock(Stage.class);
        ActionEvent event = mockEvent(stage);

        try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                (loader, context) -> when(loader.load()).thenThrow(new IOException("Fail")))) {

            assertThrows(RuntimeException.class, () -> controller.goToRecordCollectionScene(event, 1));
        }
    }

    @Test
    void testGoToCreateRecordPane_success() throws Exception {
        Stage stage = mock(Stage.class);
        ActionEvent event = mockEvent(stage);
        Parent root = mockParentWithStyleClass();
        CreateRecordSceneController mockController = mock(CreateRecordSceneController.class);

        try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                (loader, context) -> {
                    when(loader.load()).thenReturn(root);
                    when(loader.getController()).thenReturn(mockController);
                })) {

            controller.goToCreateRecordPane(event, 7);

            verify(mockController).setCollectionId(7);
            verify(stage).setScene(any(Scene.class));
            verify(stage).show();
        }
    }

    @Test
    void testGoToCreateRecordPane_loadFails_logsError() {
        Stage stage = mock(Stage.class);
        ActionEvent event = mockEvent(stage);

        try (MockedConstruction<FXMLLoader> mocked = mockConstruction(FXMLLoader.class,
                (loader, context) -> when(loader.load()).thenThrow(new IOException("Fail")))) {

            assertDoesNotThrow(() -> controller.goToCreateRecordPane(event, 8));
        }
    }
}
