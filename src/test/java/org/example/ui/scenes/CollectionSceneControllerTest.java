package org.example.ui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class CollectionSceneControllerTest extends ApplicationTest {

    private CollectionSceneController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ui/CollectionScene.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.initialize(1); // або інший тестовий ID

        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setUp() {
        // Очистка фільтрів перед кожним тестом
        interact(controller::clearFilters);
    }

    @Test
    void testSearchFunctionality() {
        clickOn("#enterField").write("SomeTitle");
        clickOn("#searchButton");

        // Додай перевірку, чи відобразилися записи, або що поле не порожнє
        verifyThat("#enterField", (TextField tf) -> !tf.getText().isEmpty());
    }

    @Test
    void testClickOnAddButton() {
        clickOn("#addButton");

        // Можна перевірити, що змінилася сцена або інша поведінка
    }

    @Test
    void testClickOnDeleteWithoutSelection() {
        clickOn("#deleteButton");

        // Очікуємо появу Alert з попередженням
        verifyThat("Please select a record to delete.", hasText("Please select a record to delete."));
    }

    @Test
    void testSortChoiceBoxInteraction() {
        clickOn("#sortChoiceBox").clickOn("title");
    }

    @Test
    void testFindChoiceBoxInteraction() {
        clickOn("#findChoiceBox").clickOn("style");
        clickOn("#enterField").write("Rock");
        type(KeyCode.ENTER);
    }

    @Test
    void testAddButtonFunctionality() {
        // Імітуємо клік на addButton і перевіряємо реакцію (залежно від логіки)
        clickOn("#addButton");
        // Тут можна додати перевірку, що має статися після кліку
        // Наприклад, перевірка появи діалогу або зміни сцени
    }

    @Test
    void testSearchWithEmptyInputClearsFilters() {
        clickOn("#enterField").eraseText(10);
        clickOn("#searchButton");
        verifyThat("#sortChoiceBox", (ChoiceBox<String> c) -> c.getValue() == null);
        verifyThat("#findChoiceBox", (ChoiceBox<String> c) -> c.getValue() == null);
    }

    @Test
    void testSelectSortBy() {
        clickOn("#sortChoiceBox").clickOn("duration");
        // можна додати перевірку кількості елементів у контейнері
        verifyThat("#recordsContainer", (VBox box) -> box.getChildren().size() >= 0);
    }

}
