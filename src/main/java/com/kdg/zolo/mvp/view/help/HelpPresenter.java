package com.kdg.zolo.mvp.view.help;

import com.kdg.zolo.mvp.view.home.HomePresenter;
import com.kdg.zolo.mvp.view.home.HomeView;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelpPresenter {

    private final HelpView view;
    private final Stage stage;

    public HelpPresenter(HelpView view, Stage stage) {
        this.view = view;
        this.stage = stage;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBackButton().setOnAction(e -> navigateToHome());
    }

    private void navigateToHome() {
        HomeView homeView = new HomeView();
        new HomePresenter(homeView, stage);
        stage.setScene(new Scene(homeView));
        stage.setTitle("Zolo - KdG");
    }
}
