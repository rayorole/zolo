package com.kdg.zolo.mvp.view.about;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;

public class AboutView extends BorderPane {

    private Button backButton;

    public AboutView() {
        backButton = new Button("Terug");
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 8px 16px; -fx-background-color: black; -fx-text-fill: white;");

        Label titleLabel = new Label("Over Zolo");
        titleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label("Gemaakt door twee studenten");
        subtitleLabel.setStyle("-fx-font-size: 18px; -fx-font-style: italic;");

        Label member1 = new Label("Ray Orolé");
        Label member2 = new Label("Alexander Mannaerts");

        member1.setStyle("-fx-font-size: 18px;");
        member2.setStyle("-fx-font-size: 18px;");

        VBox contentBox = new VBox(15);
        contentBox.setPadding(new Insets(30));
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(titleLabel, subtitleLabel, member1, member2);

        HBox bottomBox = new HBox(backButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));

        this.setStyle("-fx-background-color: white;");
        this.setCenter(contentBox);
        this.setBottom(bottomBox);
    }

    public Button getBackButton() {
        return backButton;
    }
}
