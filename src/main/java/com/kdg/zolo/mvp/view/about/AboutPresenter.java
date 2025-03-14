package com.kdg.zolo.mvp.view.about;

import com.kdg.zolo.mvp.view.home.HomePresenter;
import com.kdg.zolo.mvp.view.home.HomeView;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class AboutPresenter {

    private final AboutView view;
    private final Stage stage;

    public AboutPresenter(AboutView view, Stage stage) {
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
    }
}
