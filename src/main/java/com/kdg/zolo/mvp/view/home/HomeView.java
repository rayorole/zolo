package com.kdg.zolo.mvp.view.home;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomeView extends VBox {
    private Button startGameButton;
    private Button infoButton;
    private Button assistanceButton;
    private Button[] gridSizeButtons;
    private String chosenGridSize;

    public HomeView() {
        initializeComponents();
        arrangeComponents();
    }

    private void initializeComponents() {
        startGameButton = new Button("Play Now");
        startGameButton.setStyle("-fx-font-size: 18px; -fx-padding: 12px 24px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10px;");

        infoButton = new Button("Info");
        infoButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-color: #2196F3; -fx-text-fill: white; -fx-border-radius: 8px;");

        assistanceButton = new Button("Help");
        assistanceButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-color: #FF9800; -fx-text-fill: white; -fx-border-radius: 8px;");

        String[] sizes = { "5x5", "7x7", "9x9", "11x11" }; // Modified size options
        gridSizeButtons = new Button[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            String size = sizes[i];
            gridSizeButtons[i] = new Button(size);
            gridSizeButtons[i].setStyle("-fx-min-width: 120px; -fx-font-size: 14px; -fx-background-color: #795548; -fx-text-fill: white; -fx-border-radius: 8px;");
            gridSizeButtons[i].setOnAction(e -> selectGridSize(size));
        }
    }

    private void arrangeComponents() {
        this.setSpacing(25);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 30px;");

        Label title = new Label("ZOLO Puzzle Game");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label gridSizeLabel = new Label("Choose Grid Size");
        gridSizeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #555555;");

        HBox gridSizeBox = new HBox(15);
        gridSizeBox.setAlignment(Pos.CENTER);
        gridSizeBox.getChildren().addAll(gridSizeButtons);

        HBox topControls = new HBox(15);
        topControls.setAlignment(Pos.TOP_RIGHT);
        topControls.getChildren().addAll(infoButton, assistanceButton);

        this.getChildren().addAll(topControls, title, gridSizeLabel, gridSizeBox, startGameButton);
    }

    public Button getStartGameButton() {
        return startGameButton;
    }

    public Button getInfoButton() {
        return infoButton;
    }

    public Button getAssistanceButton() {
        return assistanceButton;
    }

    public int getChosenGridSize() {
        if (chosenGridSize == null)
            return 5;
        return Integer.parseInt(chosenGridSize.split("x")[0]);
    }

    public void selectGridSize(String size) {
        this.chosenGridSize = size;
        System.out.println("Selected Grid Size: " + size);
    }
}