package commands;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DBTableManager;

import static org.mockito.Mockito.*;

class ExitItemTest {

    private DBTableManager repository;
    private AnchorPane scenePane;
    private Scene scene;
    private Stage stage;

    @BeforeEach
    public void setUp() {
        repository = mock(DBTableManager.class);
        scenePane = mock(AnchorPane.class);
        scene = mock(Scene.class);
        stage = mock(Stage.class);

        when(scenePane.getScene()).thenReturn(scene);
        when(scene.getWindow()).thenReturn(stage);
    }

    @Test
    public void testExecute_ClosesStage() {
        ExitItem exitItem = new ExitItem(scenePane);
        exitItem.execute();

        // Перевіряємо, що метод stage.close() був викликаний
        verify(stage, times(1)).close();
    }
}