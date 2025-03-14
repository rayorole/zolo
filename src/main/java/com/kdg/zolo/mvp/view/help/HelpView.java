package com.kdg.zolo.mvp.view.help;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class HelpView extends BorderPane {

    private Button backButton;

    public HelpView() {
        initialiseNodes();
        layoutNodes();
        applyStyles();
    }

    private void initialiseNodes() {
        backButton = new Button("Terug naar hoofdmenu");
    }

    private void layoutNodes() {
        // Header
        Label titleLabel = new Label("Spelregels");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Game instructions
        VBox instructionsBox = new VBox(20);
        instructionsBox.setAlignment(Pos.TOP_LEFT);
        instructionsBox.setPadding(new Insets(20));

        // Add sections
        instructionsBox.getChildren().addAll(
                createSection("Doel van het spel",
                        "Het doel van Zolo is om alle schepen correct op het speelbord te plaatsen volgens de gegeven hints. "
                        + "Elke hint geeft aan hoeveel scheepsdelen er in een bepaalde rij of kolom moeten staan."),
                createSection("Spelregels",
                        "1. Plaats schepen op het bord door ze te slepen.\n"
                        + "2. Schepen mogen elkaar niet raken, ook niet diagonaal.\n"
                        + "3. Het aantal scheepsdelen in elke rij en kolom moet overeenkomen met de hints.\n"
                        + "4. Je kunt een schip draaien met de 'Draaien' knop.\n"
                        + "5. Gebruik de 'Reset' knop om opnieuw te beginnen.\n"
                        + "6. Gebruik de 'Ongedaan maken' knop om je laatste zet terug te draaien."),
                createSection("Schepen",
                        "Er zijn verschillende soorten schepen:\n"
                        + "- Slagschip: 4 vakjes lang\n"
                        + "- Kruiser: 3 vakjes lang\n"
                        + "- Torpedojager: 2 vakjes lang\n"
                        + "- Onderzeeër: 1 vakje"),
                createSection("Puntentelling",
                        "Je score wordt berekend op basis van:\n"
                        + "- Hoe snel je de puzzel oplost\n"
                        + "- Hoeveel zetten je nodig had\n"
                        + "- Hoeveel hints je hebt gebruikt")
        );

        // Wrap in scroll pane
        ScrollPane scrollPane = new ScrollPane(instructionsBox);
        scrollPane.setFitToWidth(true);

        // Bottom section with back button
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));
        bottomBox.getChildren().add(backButton);

        // Layout in BorderPane
        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20, 0, 0, 0));
        topBox.getChildren().add(titleLabel);

        this.setTop(topBox);
        this.setCenter(scrollPane);
        this.setBottom(bottomBox);
    }

    private VBox createSection(String title, String content) {
        VBox section = new VBox(10);
        section.setStyle("-fx-background-color: #f0f8ff; -fx-padding: 15px; -fx-border-color: #4682b4; -fx-border-radius: 5px;");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        TextFlow contentFlow = new TextFlow();
        Text contentText = new Text(content);
        contentText.setTextAlignment(TextAlignment.JUSTIFY);
        contentFlow.getChildren().add(contentText);

        section.getChildren().addAll(titleLabel, contentFlow);
        return section;
    }

    private void applyStyles() {
        this.setStyle("-fx-background-color: white;");
        backButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold;");
    }

    public Button getBackButton() {
        return backButton;
    }
}
