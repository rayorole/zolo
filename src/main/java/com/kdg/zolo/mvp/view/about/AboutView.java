package com.kdg.zolo.mvp.view.about;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AboutView extends VBox {
    private Button backButton;

    public AboutView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        backButton = new Button("← Back");
    }

    private void layoutNodes() {
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20px;");

        Label titleLabel = new Label("About Zolo");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label authorsLabel = new Label("Created by:");
        authorsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label namesLabel = new Label("Ray Orole\nAlexander Mannaerts");
        namesLabel.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");

        Label descriptionLabel = new Label(
                "A simple battleship puzzle game created as a school project.\n" +
                        "Try to solve the puzzle by placing ships according to the hints!");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-alignment: center; -fx-padding: 10px;");

        this.getChildren().addAll(titleLabel, authorsLabel, namesLabel, descriptionLabel, backButton);
    }

    public Button getBackButton() {
        return backButton;
    }
}