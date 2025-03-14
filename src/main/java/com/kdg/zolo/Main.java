package com.kdg.zolo;

import com.kdg.zolo.mvp.view.home.HomePresenter;
import com.kdg.zolo.mvp.view.home.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private static final double MIN_WIDTH = 600;
    private static final double MIN_HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Zolo - KdG");

        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setResizable(true);
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);

        // Go to home screen
        HomeView homeView = new HomeView();
        new HomePresenter(homeView, primaryStage);

        primaryStage.setScene(new Scene(homeView, screenWidth, screenHeight));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}