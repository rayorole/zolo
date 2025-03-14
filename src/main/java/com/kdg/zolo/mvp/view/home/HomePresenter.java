package com.kdg.zolo.mvp.view.home;

import com.kdg.zolo.mvp.view.about.AboutPresenter;
import com.kdg.zolo.mvp.view.about.AboutView;
import com.kdg.zolo.mvp.view.game.GamePresenter;
import com.kdg.zolo.mvp.view.help.HelpPresenter;
import com.kdg.zolo.mvp.view.help.HelpView;
import com.kdg.zolo.mvp.view.game.GameView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePresenter {
    private final HomeView view;
    private final Stage stage;

    public HomePresenter(HomeView view, Stage stage) {
        this.view = view;
        this.stage = stage;

        this.addEventHandlers();
    }

    private void addEventHandlers() {
        view.getStartGameButton().setOnAction(e -> startGame());
        view.getInfoButton().setOnAction(e -> goToAbout());
        view.getAssistanceButton().setOnAction(e -> goToHelp());
    }

    private void startGame() {
        int selectedSize = view.getChosenGridSize();
        System.out.println("Start spel met veldgrootte: " + selectedSize + "x" + selectedSize);

        GameView gameView = new GameView(selectedSize);
        new GamePresenter(gameView, stage, selectedSize);
        stage.setScene(new Scene(gameView, 600, 600));
    }

    private void goToAbout() {
        AboutView aboutView = new AboutView();
        new AboutPresenter(aboutView, stage);
        stage.setScene(new Scene(aboutView, 400, 300));
    }

    private void goToHelp() {
        HelpView helpView = new HelpView();
        new HelpPresenter(helpView, stage);
        stage.setScene(new Scene(helpView, 400, 300));
    }
}
