package org.example.ui.scenesHelpers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SceneControllerTest {

    private SceneController sceneController;

    @BeforeEach
    void setup() {
        sceneController = new SceneController();
    }

//    @Test
//    void testGoBackToMainScene_success() throws Exception {
//        // Mock all the JavaFX objects
//        ActionEvent event = mock(ActionEvent.class);
//        Node sourceNode = mock(Node.class);
//        Scene scene = mock(Scene.class);
//        Stage stage = mock(Stage.class);
//        Parent root = mock(Parent.class);
//
//        // Stub getSource -> Node -> Scene -> Window (Stage)
//        when(event.getSource()).thenReturn(sourceNode);
//        when(sourceNode.getScene()).thenReturn(scene);
//        when(scene.getWindow()).thenReturn(stage);
//
//        // Mock FXMLLoader static load method
//        try (MockedStatic<javafx.fxml.FXMLLoader> loaderStatic = Mockito.mockStatic(javafx.fxml.FXMLLoader.class)) {
//            loaderStatic.when(() -> javafx.fxml.FXMLLoader.load(any(java.net.URL.class))).thenReturn(root);
//
//            // Mock getResource to avoid NPE for CSS loading
//            SceneController controllerSpy = spy(sceneController);
//            controllerSpy.goBackToMainScene(event);
//
//            // Call method under test
//            controllerSpy.goBackToMainScene(event);
//
//            // Verify stage setScene and show called
//            verify(stage).setScene(any(Scene.class));
//            verify(stage).show();
//        }
//    }

    @Test
    void testGoBackToMainScene_fxmlLoadThrowsRuntimeException() throws Exception {
        ActionEvent event = mock(ActionEvent.class);
        Node sourceNode = mock(Node.class);
        Scene scene = mock(Scene.class);
        Stage stage = mock(Stage.class);

        when(event.getSource()).thenReturn(sourceNode);
        when(sourceNode.getScene()).thenReturn(scene);
        when(scene.getWindow()).thenReturn(stage);

        try (MockedStatic<javafx.fxml.FXMLLoader> loaderStatic = Mockito.mockStatic(javafx.fxml.FXMLLoader.class)) {
            loaderStatic.when(() -> javafx.fxml.FXMLLoader.load(any(java.net.URL.class))).thenThrow(new IOException("Fail loading FXML"));

            assertThrows(RuntimeException.class, () -> sceneController.goBackToMainScene(event));
        }
    }

}
