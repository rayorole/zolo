package com.kdg.zolo.mvp.view.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class HomeView extends BorderPane {

    private Button playButton;
    private Button highScoreButton;
    private Button helpButton;
    private Button aboutButton;
    private Button exitButton;
    private ComboBox<String> difficultyComboBox;
    private MenuBar menuBar;
    private MenuItem newGameMenuItem;
    private MenuItem highScoreMenuItem;
    private MenuItem helpMenuItem;
    private MenuItem aboutMenuItem;
    private MenuItem exitMenuItem;

    public HomeView() {
        initialiseNodes();
        layoutNodes();
        applyStyles();
    }

    private void initialiseNodes() {
        // Main buttons
        playButton = new Button("Speel");
        highScoreButton = new Button("Highscores");
        helpButton = new Button("Help");
        aboutButton = new Button("Over Zolo");
        exitButton = new Button("Afsluiten");

        // Difficulty selection
        difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.setValue("Medium");

        // Menu bar
        menuBar = new MenuBar();

        Menu gameMenu = new Menu("Spel");
        newGameMenuItem = new MenuItem("Nieuw Spel");
        exitMenuItem = new MenuItem("Afsluiten");
        gameMenu.getItems().addAll(newGameMenuItem, exitMenuItem);

        Menu viewMenu = new Menu("Weergave");
        highScoreMenuItem = new MenuItem("Highscores");
        viewMenu.getItems().add(highScoreMenuItem);

        Menu helpMenu = new Menu("Help");
        helpMenuItem = new MenuItem("Spelregels");
        aboutMenuItem = new MenuItem("Over Zolo");
        helpMenu.getItems().addAll(helpMenuItem, aboutMenuItem);

        menuBar.getMenus().addAll(gameMenu, viewMenu, helpMenu);
    }

    private void layoutNodes() {
        // Top section with menu bar
        this.setTop(menuBar);

        // Center section with title and buttons
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));

        Label titleLabel = new Label("ZOLO");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 48));
        titleLabel.setTextAlignment(TextAlignment.CENTER);

        Label subtitleLabel = new Label("Battleship Puzzle Game");
        subtitleLabel.setFont(Font.font("System", FontWeight.NORMAL, 20));
        subtitleLabel.setTextAlignment(TextAlignment.CENTER);

        // Difficulty selection
        HBox difficultyBox = new HBox(10);
        difficultyBox.setAlignment(Pos.CENTER);
        Label difficultyLabel = new Label("Moeilijkheidsgraad:");
        difficultyLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        difficultyBox.getChildren().addAll(difficultyLabel, difficultyComboBox);

        // Style buttons
        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(30, 0, 0, 0));

        // Set button sizes
        double buttonWidth = 200;
        double buttonHeight = 40;

        playButton.setPrefSize(buttonWidth, buttonHeight);
        highScoreButton.setPrefSize(buttonWidth, buttonHeight);
        helpButton.setPrefSize(buttonWidth, buttonHeight);
        aboutButton.setPrefSize(buttonWidth, buttonHeight);
        exitButton.setPrefSize(buttonWidth, buttonHeight);

        buttonBox.getChildren().addAll(
                playButton,
                highScoreButton,
                helpButton,
                aboutButton,
                exitButton
        );

        centerBox.getChildren().addAll(titleLabel, subtitleLabel, difficultyBox, buttonBox);
        this.setCenter(centerBox);
    }

    private void applyStyles() {
        // Apply styles to the view
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #1e5799, #7db9e8);");

        // Style for buttons
        String buttonStyle = "-fx-background-color: #4682b4; "
                + "-fx-text-fill: white; "
                + "-fx-font-weight: bold; "
                + "-fx-font-size: 14px; "
                + "-fx-background-radius: 5px;";

        playButton.setStyle(buttonStyle + "-fx-background-color: #2E8B57;");
        highScoreButton.setStyle(buttonStyle);
        helpButton.setStyle(buttonStyle);
        aboutButton.setStyle(buttonStyle);
        exitButton.setStyle(buttonStyle + "-fx-background-color: #B22222;");

        // Style for combo box
        difficultyComboBox.setStyle("-fx-font-size: 14px;");
    }

    // Getters for the buttons
    public Button getPlayButton() {
        return playButton;
    }

    public Button getHighScoreButton() {
        return highScoreButton;
    }

    public Button getHelpButton() {
        return helpButton;
    }

    public Button getAboutButton() {
        return aboutButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public String getSelectedDifficulty() {
        return difficultyComboBox.getValue().toLowerCase();
    }

    // Getters for menu items
    public MenuItem getNewGameMenuItem() {
        return newGameMenuItem;
    }

    public MenuItem getHighScoreMenuItem() {
        return highScoreMenuItem;
    }

    public MenuItem getHelpMenuItem() {
        return helpMenuItem;
    }

    public MenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public MenuItem getExitMenuItem() {
        return exitMenuItem;
    }
}
