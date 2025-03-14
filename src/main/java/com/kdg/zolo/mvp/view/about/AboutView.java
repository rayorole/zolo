package com.kdg.zolo.mvp.view.about;

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

public class AboutView extends BorderPane {

    private Button backButton;
    private VBox teamMembersBox;

    public AboutView() {
        initialiseNodes();
        layoutNodes();
        applyStyles();
    }

    private void initialiseNodes() {
        backButton = new Button("Terug naar hoofdmenu");
        teamMembersBox = new VBox(20);
    }

    private void layoutNodes() {
        // Header
        Label titleLabel = new Label("Over Zolo");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Team members section
        addTeamMember("Student 1", "Rol: Ontwikkelaar",
                "Verantwoordelijk voor de game logica en model implementatie.");
        addTeamMember("Student 2", "Rol: UI Designer",
                "Verantwoordelijk voor de gebruikersinterface en gebruikerservaring.");
        addTeamMember("Student 3", "Rol: Tester",
                "Verantwoordelijk voor het testen van de applicatie en bugfixing.");

        // Game description
        TextFlow descriptionFlow = new TextFlow();
        Text descriptionText = new Text(
                "Zolo is een puzzelspel gebaseerd op het klassieke Battleship spel. "
                + "Het doel is om schepen op het speelbord te plaatsen volgens de gegeven hints. "
                + "Elke hint geeft aan hoeveel scheepsdelen er in een bepaalde rij of kolom moeten staan. "
                + "\n\nDe speler moet alle schepen correct plaatsen om de puzzel op te lossen. "
                + "Hoe sneller je de puzzel oplost, hoe hoger je score!"
        );
        descriptionText.setTextAlignment(TextAlignment.JUSTIFY);
        descriptionFlow.getChildren().add(descriptionText);
        descriptionFlow.setPadding(new Insets(20));

        // Wrap content in scroll pane
        ScrollPane scrollPane = new ScrollPane();
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.getChildren().addAll(titleLabel, teamMembersBox, descriptionFlow);
        contentBox.setPadding(new Insets(20));
        scrollPane.setContent(contentBox);
        scrollPane.setFitToWidth(true);

        // Bottom section with back button
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));
        bottomBox.getChildren().add(backButton);

        // Layout in BorderPane
        this.setCenter(scrollPane);
        this.setBottom(bottomBox);
    }

    private void addTeamMember(String name, String role, String description) {
        VBox memberBox = new VBox(5);
        memberBox.setStyle("-fx-background-color: #f0f8ff; -fx-padding: 10px; -fx-border-color: #4682b4; -fx-border-radius: 5px;");

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        Label roleLabel = new Label(role);
        roleLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);

        memberBox.getChildren().addAll(nameLabel, roleLabel, descLabel);
        teamMembersBox.getChildren().add(memberBox);
    }

    private void applyStyles() {
        this.setStyle("-fx-background-color: white;");
        backButton.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-weight: bold;");
    }

    public Button getBackButton() {
        return backButton;
    }
}
