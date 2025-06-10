package org.example.ui.scenesHelpers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.models.Record;

public class RecordCard {
    @FXML
    VBox recordsContainer;

    public void setRecordContainer(VBox recordsContainer) {
        this.recordsContainer = recordsContainer;
    }

    public VBox createRecordCard(Record record) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("record-card");

        HBox header = createHeader(record);
        VBox moreInfoBanner = createMoreInfoBanner(record);
        Label titleLabel = createTitle(record);
        HBox styleWithMore = styleBoxAndMore(record, moreInfoBanner);

        card.getChildren().addAll(header, titleLabel, styleWithMore, moreInfoBanner);
        return card;
    }

    private HBox createHeader(Record record) {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("record-card-header");

        Label idBadge = new Label("#" + record.getId());
        idBadge.getStyleClass().add("record-id-badge");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label durationLabel = new Label("⏱ " + record.getDuration());
        durationLabel.getStyleClass().add("record-duration-label");

        header.getChildren().addAll(idBadge, spacer, durationLabel);
        return header;
    }

    private Label createTitle(Record record) {
        Label titleLabel = new Label(record.getTitle());
        titleLabel.getStyleClass().add("record-title-label");
        titleLabel.setWrapText(true);
        return titleLabel;
    }

    private HBox styleBoxAndMore(Record record, VBox moreInfoBanner) {
        HBox styleBox = createStyleBox(record);
        HBox styleWithMore = new HBox();
        styleWithMore.setAlignment(Pos.CENTER_LEFT);
        styleWithMore.setSpacing(10);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button moreButton = createMoreButton(moreInfoBanner);

        styleWithMore.getChildren().addAll(styleBox, spacer, moreButton);

        return styleWithMore;
    }

    private HBox createStyleBox(Record record) {
        HBox styleBox = new HBox(8);
        styleBox.setAlignment(Pos.CENTER_LEFT);
        styleBox.getStyleClass().add("record-style-box");

        Label styleIcon = new Label("🎵");
        styleIcon.getStyleClass().add("record-style-icon");

        Label styleLabel = new Label(record.getStyle());
        styleLabel.getStyleClass().add("record-style-label");

        styleBox.getChildren().addAll(styleIcon, styleLabel);
        return styleBox;
    }

    private VBox createMoreInfoBanner(Record record) {
        VBox moreInfoBanner = new VBox(8);
        moreInfoBanner.setPadding(new Insets(10));
        moreInfoBanner.setStyle("-fx-background-color: #f1f3f5; -fx-border-color: #adb5bd; -fx-border-radius: 5;");
        moreInfoBanner.setVisible(false);
        moreInfoBanner.setManaged(false);

        // Close button
        HBox closeBar = new HBox();
        closeBar.setAlignment(Pos.TOP_RIGHT);
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-font-weight: bold;");
        closeBar.getChildren().add(closeButton);

        // Info labels
        Label authorLabel = new Label("Author: " + record.getAuthor());
        Label descriptionLabel = new Label("Description: " + record.getDescription());
        descriptionLabel.setWrapText(true);

        moreInfoBanner.getChildren().addAll(closeBar, authorLabel, descriptionLabel);

        closeButton.setOnAction(e -> {
            moreInfoBanner.setVisible(false);
            moreInfoBanner.setManaged(false);
        });

        return moreInfoBanner;
    }

    private Button createMoreButton(VBox moreInfoBanner) {
        Button moreButton = new Button("⋯");
        moreButton.getStyleClass().add("more-button");
        moreButton.setAlignment(Pos.TOP_RIGHT);

        moreButton.setOnAction(e -> {
            moreInfoBanner.setVisible(true);
            moreInfoBanner.setManaged(true);
        });
        return moreButton;
    }

    public void showEmptyState(VBox recordsContainer) {
        VBox emptyState = new VBox(20);
        emptyState.setAlignment(Pos.CENTER);
        emptyState.setPadding(new Insets(60));
        emptyState.getStyleClass().add("empty-state-container");

        Label emptyIcon = new Label("📀");
        emptyIcon.getStyleClass().add("empty-state-icon");

        Label emptyTitle = new Label("No Records Found");
        emptyTitle.getStyleClass().add("empty-state-title");

        Label emptyDescription = new Label("Start by adding your first record to this collection");
        emptyDescription.getStyleClass().add("empty-state-description");
        emptyDescription.setWrapText(true);

        emptyState.getChildren().addAll(emptyIcon, emptyTitle, emptyDescription);
        recordsContainer.getChildren().add(emptyState);
    }
}
