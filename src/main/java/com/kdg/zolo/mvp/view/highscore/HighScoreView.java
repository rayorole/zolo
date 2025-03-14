package com.kdg.zolo.mvp.view.highscore;

import java.util.List;

import com.kdg.zolo.mvp.util.ScoreEntry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HighScoreView extends BorderPane {

    private TableView<ScoreEntry> scoreTable;
    private Button backButton;
    private ObservableList<ScoreEntry> scoreData;

    public HighScoreView() {
        initialiseNodes();
        layoutNodes();
        applyStyles();
    }

    private void initialiseNodes() {
        // Create table
        scoreTable = new TableView<>();
        scoreData = FXCollections.observableArrayList();
        scoreTable.setItems(scoreData);

        // Create columns
        TableColumn<ScoreEntry, String> rankColumn = new TableColumn<>("Rang");
        rankColumn.setCellValueFactory(cellData
                -> new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(scoreData.indexOf(cellData.getValue()) + 1)
                )
        );

        TableColumn<ScoreEntry, String> nameColumn = new TableColumn<>("Naam");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());

        TableColumn<ScoreEntry, Number> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());

        TableColumn<ScoreEntry, String> timeColumn = new TableColumn<>("Tijd");
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());

        // Set column widths
        rankColumn.setPrefWidth(50);
        nameColumn.setPrefWidth(200);
        scoreColumn.setPrefWidth(100);
        timeColumn.setPrefWidth(100);

        // Add columns to table
        scoreTable.getColumns().addAll(rankColumn, nameColumn, scoreColumn, timeColumn);

        // Create back button
        backButton = new Button("Terug naar hoofdmenu");
    }

    private void layoutNodes() {
        // Header
        Label titleLabel = new Label("Highscores");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20, 0, 20, 0));
        topBox.getChildren().add(titleLabel);

        // Center with table
        VBox centerBox = new VBox();
        centerBox.setPadding(new Insets(0, 20, 0, 20));
        centerBox.getChildren().add(scoreTable);

        // Bottom with back button
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        bottomBox.getChildren().add(backButton);

        // Set layout
        this.setTop(topBox);
        this.setCenter(centerBox);
        this.setBottom(bottomBox);
    }

    private void applyStyles() {
        this.setStyle("-fx-background-color: white;");

        scoreTable.setStyle("-fx-font-size: 14px;");
        scoreTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        backButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold;");
    }

    public void updateScores(List<ScoreEntry> scores) {
        scoreData.clear();
        scoreData.addAll(scores);
    }

    public Button getBackButton() {
        return backButton;
    }
}
