package pt.isec.pa.tinypac.ui.gui.views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainMenuPane extends BorderPane {

    private VBox menu;
    private ToggleGroup tgMenuButtons;

    private ToggleButton btnPlayGame, btnTop5 ,btnExitGame;

    private static final Integer BTN_PREF_WIDTH = 100;
    public MainMenuPane() {

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        tgMenuButtons = new ToggleGroup();

        btnPlayGame = new ToggleButton("Play Game");
        btnPlayGame.setToggleGroup(tgMenuButtons);
        btnPlayGame.setPrefHeight(100);
        btnPlayGame.setPrefWidth(100);
        btnPlayGame.setSelected(false);
        btnPlayGame.setPadding(new Insets(10));

        btnTop5 = new ToggleButton("Top 5");
        btnTop5.setToggleGroup(tgMenuButtons);
        btnTop5.setPrefHeight(100);
        btnTop5.setPrefWidth(100);
        btnTop5.setSelected(false);
        btnTop5.setPadding(new Insets(10));

        btnExitGame = new ToggleButton("Exit");
        btnExitGame.setToggleGroup(tgMenuButtons);
        btnExitGame.setPrefHeight(100);
        btnExitGame.setPrefWidth(100);
        btnExitGame.setSelected(false);
        btnExitGame.setPadding(new Insets(10));

        menu = new VBox(btnPlayGame, btnTop5,btnExitGame);
        menu.setMinWidth(250);
        menu.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(menu);
        menu.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {

        btnPlayGame.setOnAction(actionEvent -> {
            GamePane gamePane = new GamePane();
            ((BorderPane)this.getScene().getRoot()).setCenter(gamePane);
        });


        btnTop5.setOnAction(actionEvent -> {

            Top5Pane top5 = new Top5Pane();
            ((RootPane)this.getScene().getRoot()).setCenter(top5);

        });

        btnExitGame.setOnAction(actionEvent -> {
            Platform.exit();
        });

    }

    private void update() {

    }

}
