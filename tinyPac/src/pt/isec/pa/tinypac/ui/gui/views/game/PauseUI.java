package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;

public class PauseUI extends BorderPane {

    private ModelManager manager;

    private ToggleButton btnContinue;

    private ToggleButton btnSaveExit;
    private ToggleButton btnExit;

    private static final Integer MENU_MIN_WIDTH = 150;
    private static final Integer BTN_PREF_WIDTH = 150;
    private static final Integer BTN_MAX_WIDTH = 200;
    private static final Integer BTN_PREF_HEIGHT = 100;
    private static final Integer BTN_SPACING = 5;

    public PauseUI(ModelManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
        requestFocus();
    }

    private void createViews() {

        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        //Menu
        VBox vBox = new VBox();
        Label lbState = new Label("Game Paused");
        lbState.setAlignment(Pos.CENTER);

        //Buttons
        ToggleGroup tgMenuButtons = new ToggleGroup();
        btnContinue = new ToggleButton("Continue Game");
        btnContinue.setToggleGroup(tgMenuButtons);
        btnContinue.setPrefHeight(BTN_PREF_HEIGHT);
        btnContinue.setPrefWidth(BTN_PREF_WIDTH);
        btnContinue.setMaxWidth(BTN_MAX_WIDTH);
        btnContinue.setStyle(
                "-fx-background-radius: 5em; "
        );
        btnContinue.setSelected(false);

        btnSaveExit = new ToggleButton("Save and Exit");
        btnSaveExit.setToggleGroup(tgMenuButtons);
        btnSaveExit.setPrefHeight(BTN_PREF_HEIGHT);
        btnSaveExit.setPrefWidth(BTN_PREF_WIDTH);
        btnSaveExit.setMaxWidth(BTN_MAX_WIDTH);
        btnSaveExit.setStyle(
                "-fx-background-radius: 5em; "
        );
        btnSaveExit.setSelected(false);

        btnExit = new ToggleButton("Exit Game");
        btnExit.setToggleGroup(tgMenuButtons);
        btnExit.setPrefHeight(BTN_PREF_HEIGHT);
        btnExit.setPrefWidth(BTN_PREF_WIDTH);
        btnExit.setMaxWidth(BTN_MAX_WIDTH);
        btnExit.setStyle(
                "-fx-background-radius: 5em; "
        );
        btnExit.setSelected(false);

        HBox hBoxButtons = new HBox(btnContinue,btnSaveExit,btnExit);
        hBoxButtons.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(lbState,hBoxButtons);
        this.setCenter(hBoxButtons);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> update());
        //manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> updateState());
    }

    private void update(){
        if(manager.getState() != State.PAUSE){
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
