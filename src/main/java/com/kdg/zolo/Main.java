package com.kdg.zolo;

import com.kdg.zolo.mvp.view.home.HomePresenter;
import com.kdg.zolo.mvp.view.home.HomeView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private static final double MIN_WIDTH = 800;
    private static final double MIN_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Zolo - KdG");

        // Set window properties
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth() * 0.8;
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight() * 0.8;

        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setResizable(true);
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);

        // Go home screen
        HomeView homeView = new HomeView();
        new HomePresenter(homeView, primaryStage);

        primaryStage.setScene(new Scene(homeView, screenWidth, screenHeight));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
