package org.example.ui.scenesHelpers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.models.Record;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class RecordCardTest {

    static boolean javafxStarted = false;

    @BeforeAll
    public static void initJfx() throws Exception {
        if (!javafxStarted) {
            CountDownLatch latch = new CountDownLatch(1);
            new Thread(() -> {
                Application.launch(DummyApp.class);
                latch.countDown();
            }).start();
            // Чекаємо старту JavaFX
            Thread.sleep(500);
            javafxStarted = true;
        }
    }

    @Test
    public void testCreateRecordCard() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            RecordCard recordCard = new RecordCard();
            Record record = new Record("Test Title", "Jazz", LocalTime.of(0, 4, 45), "AuthorName", "Some description");
            record.setId(10);
            VBox card = recordCard.createRecordCard(record);

            // Перевірка структури VBox card
            assertNotNull(card);
            assertTrue(card.getStyleClass().contains("record-card"));
            assertEquals(4, card.getChildren().size());

            // Перевіряємо Header
            Node headerNode = card.getChildren().get(0);
            assertTrue(headerNode instanceof HBox);
            HBox header = (HBox) headerNode;
            assertTrue(header.getStyleClass().contains("record-card-header"));
            assertEquals(3, header.getChildren().size());

            // Перевіряємо Title Label
            Node titleNode = card.getChildren().get(1);
            assertTrue(titleNode instanceof Label);
            Label titleLabel = (Label) titleNode;
            assertEquals("Test Title", titleLabel.getText());
            assertTrue(titleLabel.getStyleClass().contains("record-title-label"));

            // Перевіряємо styleWithMore HBox
            Node styleWithMoreNode = card.getChildren().get(2);
            assertTrue(styleWithMoreNode instanceof HBox);
            HBox styleWithMore = (HBox) styleWithMoreNode;
            assertEquals(3, styleWithMore.getChildren().size());

            // Перевіряємо, що є кнопка More і вона працює
            Button moreButton = null;
            for (Node node : styleWithMore.getChildren()) {
                if (node instanceof Button) {
                    moreButton = (Button) node;
                    break;
                }
            }
            assertNotNull(moreButton);
            // Спочатку moreInfoBanner невидимий
            VBox moreInfoBanner = (VBox) card.getChildren().get(3);
            assertFalse(moreInfoBanner.isVisible());
            assertFalse(moreInfoBanner.isManaged());

            // Клік по More кнопці робить банер видимим
            moreButton.fire();
            assertTrue(moreInfoBanner.isVisible());
            assertTrue(moreInfoBanner.isManaged());

            // Перевіряємо close кнопку в moreInfoBanner
            Button closeButton = null;
            for (Node n : ((HBox) moreInfoBanner.getChildren().get(0)).getChildren()) {
                if (n instanceof Button) {
                    closeButton = (Button) n;
                    break;
                }
            }
            assertNotNull(closeButton);

            // Клік по close кнопці ховає moreInfoBanner
            closeButton.fire();
            assertFalse(moreInfoBanner.isVisible());
            assertFalse(moreInfoBanner.isManaged());

            latch.countDown();
        });

        assertTrue(latch.await(2, TimeUnit.SECONDS));
    }

    @Test
    public void testShowEmptyState() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            RecordCard recordCard = new RecordCard();
            VBox container = new VBox();

            recordCard.showEmptyState(container);

            assertEquals(1, container.getChildren().size());
            Node emptyState = container.getChildren().get(0);
            assertTrue(emptyState instanceof VBox);

            VBox emptyBox = (VBox) emptyState;
            assertTrue(emptyBox.getStyleClass().contains("empty-state-container"));
            assertEquals(3, emptyBox.getChildren().size());

            Label icon = (Label) emptyBox.getChildren().get(0);
            assertEquals("📀", icon.getText());
            assertTrue(icon.getStyleClass().contains("empty-state-icon"));

            Label title = (Label) emptyBox.getChildren().get(1);
            assertEquals("No Records Found", title.getText());
            assertTrue(title.getStyleClass().contains("empty-state-title"));

            Label desc = (Label) emptyBox.getChildren().get(2);
            assertEquals("Start by adding your first record to this collection", desc.getText());
            assertTrue(desc.getStyleClass().contains("empty-state-description"));
            assertTrue(desc.isWrapText());

            latch.countDown();
        });

        assertTrue(latch.await(2, TimeUnit.SECONDS));
    }

    // Dummy JavaFX app for initialization
    public static class DummyApp extends Application {
        @Override
        public void start(javafx.stage.Stage primaryStage) {
        }
    }
}
