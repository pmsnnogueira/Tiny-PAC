package pt.isec.pa.tinypac.ui.gui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Top5Pane extends BorderPane {

    private VBox menu;
    private ToggleGroup tgMenuButtons;

    private ToggleButton btnExitGame;

    private static final Integer BTN_PREF_WIDTH = 100;
    public Top5Pane() {

        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void createViews() {

        tgMenuButtons = new ToggleGroup();

        btnExitGame = new ToggleButton("Exit");
        btnExitGame.setToggleGroup(tgMenuButtons);
        btnExitGame.setPrefHeight(100);
        btnExitGame.setPrefWidth(100);
        btnExitGame.setSelected(false);
        btnExitGame.setPadding(new Insets(10));

        menu = new VBox(btnExitGame);
        menu.setMinWidth(250);
        menu.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(menu);
        menu.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {

        btnExitGame.setOnAction(actionEvent -> {

            MainMenuPane mainMenu = new MainMenuPane();
            ((BorderPane)this.getScene().getRoot()).setCenter(mainMenu);

        });
    }
}
