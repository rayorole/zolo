package com.kdg.zolo.mvp.view.help;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HelpView extends VBox {
    private Button backButton;

    public HelpView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        backButton = new Button("← Terug");
    }

    private void layoutNodes() {
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20px;");

        Label titleLabel = new Label("How to Play");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label helpText = new Label(
                "1. Ships are hidden on the board\n" +
                        "2. Numbers show how many ship squares are in each row/column\n" +
                        "3. Click cells to cycle between empty, ship, and water\n" +
                        "4. Fill all squares correctly to solve the puzzle!");
        helpText.setWrapText(true);
        helpText.setStyle("-fx-font-size: 16px; -fx-padding: 20px; -fx-text-alignment: center;");

        this.getChildren().addAll(titleLabel, helpText, backButton);
    }

    public Button getBackButton() {
        return backButton;
    }
}