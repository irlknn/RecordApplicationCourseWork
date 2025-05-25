//package org.example.ui.scenesHelpers;
//
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//public class ConfirmBanner {
//    public static void show(String title, String message, Runnable onConfirm) {
//        Stage window = new Stage();
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle(title);
//        window.setMinWidth(300);
//
//        Label label = new Label(message);
//        Button yesButton = new Button("Так");
//        Button cancelButton = new Button("Скасувати");
//
//        yesButton.setOnAction(e -> {
//            onConfirm.run();
//            window.close();
//        });
//
//        cancelButton.setOnAction(e -> window.close());
//
//        HBox buttonLayout = new HBox(10, yesButton, cancelButton);
//        buttonLayout.setAlignment(Pos.CENTER);
//
//        VBox layout = new VBox(15, label, buttonLayout);
//        layout.setAlignment(Pos.CENTER);
//        layout.setPadding(new Insets(20));
//
//        Scene scene = new Scene(layout);
//        window.setScene(scene);
//        window.showAndWait();
//    }
//}
