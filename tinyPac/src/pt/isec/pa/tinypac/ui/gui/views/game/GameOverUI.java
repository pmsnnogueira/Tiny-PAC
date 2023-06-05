package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class GameOverUI extends BorderPane {
    private ModelManager manager;

    private ToggleButton btnBackToMainMenu;
    private ToggleButton btnExit;

    private static final Integer MENU_MIN_WIDTH = 150;

    private static final Integer BTN_MIN_WIDTH = 170;
    private static final Integer BTN_PREF_WIDTH = 180;
    private static final Integer BTN_MAX_WIDTH = 200;


    private static final Integer BTN_MIN_HEIGHT = 25;
    private static final Integer BTN_PREF_HEIGHT = 50;
    private static final Integer BTN_MAX_HEIGHT = 50;
    private static final Integer BTN_SPACING = 20;

    public GameOverUI(ModelManager manager){
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
        requestFocus();
    }

    private void createViews() {

        this.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("background_Gameover.jpg"),
                                BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,true,false)
                        )
        ));

        //Menu
        VBox vBox = new VBox();
        Label lbPoints = new Label("Score: " + manager.getScore());
        lbPoints.getStyleClass().add("mainLabel");
        lbPoints.setAlignment(Pos.CENTER);

        //Buttons
        ToggleGroup tgMenuButtons = new ToggleGroup();
        btnBackToMainMenu = new ToggleButton("Back To Main Menu");
        btnBackToMainMenu.setToggleGroup(tgMenuButtons);
        btnBackToMainMenu.setMinWidth(BTN_MIN_WIDTH);
        btnBackToMainMenu.setPrefWidth(BTN_PREF_WIDTH);
        btnBackToMainMenu.setMaxWidth(BTN_MAX_WIDTH);
        btnBackToMainMenu.setPrefHeight(BTN_PREF_HEIGHT);
        btnBackToMainMenu.setMinHeight(BTN_MIN_HEIGHT);
        btnBackToMainMenu.setMaxHeight(BTN_MAX_HEIGHT);

        btnBackToMainMenu.getStyleClass().add("gameOverButton");
        btnBackToMainMenu.setSelected(false);

        btnExit = new ToggleButton("Exit Game");
        btnExit.setToggleGroup(tgMenuButtons);
        btnExit.setMinWidth(BTN_MIN_WIDTH);
        btnExit.setPrefWidth(BTN_PREF_WIDTH);
        btnExit.setMaxWidth(BTN_MAX_WIDTH);
        btnExit.setPrefHeight(BTN_PREF_HEIGHT);
        btnExit.setMinHeight(BTN_MIN_HEIGHT);
        btnExit.setMaxHeight(BTN_MAX_HEIGHT);
        btnExit.getStyleClass().add("gameOverButton");
        btnExit.setSelected(false);

        HBox hBoxButtons = new HBox(btnBackToMainMenu,btnExit);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(BTN_SPACING);

        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(50,0,0,15));
        vBox.setSpacing(5);

        vBox.getChildren().addAll(lbPoints,hBoxButtons);
        this.setCenter(vBox);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> update());
        //manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> updateState());

        btnBackToMainMenu.setOnAction(actionEvent -> {
            manager.changeToMainMenu();
        });

        btnExit.setOnAction(actionEvent -> {
            Platform.exit();
        });
    }

    private void update(){
        if(manager.getProgramState() != ProgramManager.GAME || manager.getState() != State.GameOver){
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
