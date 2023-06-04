package pt.isec.pa.tinypac.ui.gui.views.menu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.views.RootPane;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class MainMenuPane extends BorderPane {
    private GameRootPane gameRootPane;

    private ToggleButton btnPlayGame, btnTop5 ,btnExitGame;
    private static final Integer BTN_MIN_WIDTH = 100;
    private static final Integer BTN_PREF_WIDTH = 120;
    private static final Integer BTN_MAX_WIDTH = 150;


    private static final Integer BTN_MIN_HEIGHT = 50;
    private static final Integer BTN_PREF_HEIGHT = 75;
    private static final Integer BTN_MAX_HEIGHT = 80;

    private static final Integer BTN_SPACING = 15;
    private ModelManager manager;

    public MainMenuPane(ModelManager manager) {

        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        ToggleGroup tgMenuButtons = new ToggleGroup();

        btnPlayGame = new ToggleButton("Play Game");
        btnPlayGame.setMinHeight(BTN_MIN_HEIGHT);
        btnPlayGame.setPrefHeight(BTN_PREF_HEIGHT);
        btnPlayGame.setMaxHeight(BTN_MAX_HEIGHT);

        btnPlayGame.setMinWidth(BTN_MIN_WIDTH);
        btnPlayGame.setPrefWidth(BTN_PREF_WIDTH);
        btnPlayGame.setMaxWidth(BTN_MAX_WIDTH);
        btnPlayGame.setStyle(
                "-fx-background-radius: 2.5em; "+
                    "-fx-background-color: linear-gradient(from 25px 25px to 100px 100px, #DAA442, #797574); "
        );
        btnPlayGame.setSelected(false);

        btnTop5 = new ToggleButton("Top 5");
        btnTop5.setMinHeight(BTN_MIN_HEIGHT);
        btnTop5.setPrefHeight(BTN_PREF_HEIGHT);
        btnTop5.setMaxHeight(BTN_MAX_HEIGHT);

        btnTop5.setMinWidth(BTN_MIN_WIDTH);
        btnTop5.setPrefWidth(BTN_PREF_WIDTH);
        btnTop5.setMaxWidth(BTN_MAX_WIDTH);
        btnTop5.setStyle(
                "-fx-background-radius: 2.5em; "+
                        "-fx-background-color: linear-gradient(from 25px 25px to 100px 100px, #DAA442, #797574); "
        );
        btnTop5.setSelected(false);

        btnExitGame = new ToggleButton("Exit");
        btnExitGame.setMinHeight(BTN_MIN_HEIGHT);
        btnExitGame.setPrefHeight(BTN_PREF_HEIGHT);
        btnExitGame.setMaxHeight(BTN_MAX_HEIGHT);

        btnExitGame.setMinWidth(BTN_MIN_WIDTH);
        btnExitGame.setPrefWidth(BTN_PREF_WIDTH);
        btnExitGame.setMaxWidth(BTN_MAX_WIDTH);
        btnExitGame.setStyle(
                "-fx-background-radius: 2.5em; "+
                        "-fx-background-color: linear-gradient(from 25px 25px to 100px 100px, #DAA442, #797574); "
        );
        btnExitGame.setSelected(false);

        tgMenuButtons.getToggles().addAll(btnPlayGame, btnTop5,btnExitGame);

        VBox menu = new VBox(btnPlayGame, btnTop5,btnExitGame);
        menu.setSpacing(BTN_SPACING);
        menu.setPadding(new Insets(50,0,0,0));

        this.setCenter(menu);
        menu.setAlignment(Pos.CENTER);
        //this.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("background_MainMenu.jpg"),
                                BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(100,100,true,true,true,false)
                        )
        ));
    }

    private void registerHandlers() {

        btnPlayGame.setOnAction(actionEvent -> {
            manager.changeToGame();
        });

        btnTop5.setOnAction(actionEvent -> {
            manager.changeToTop5();
        });

        btnExitGame.setOnAction(actionEvent -> {
            Platform.exit();
        });

    }

    private void updateState(){
        if(manager.getProgramState() != ProgramManager.MAIN_MENU){
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

    private void update() {
        this.setVisible(manager.getProgramState() == ProgramManager.MAIN_MENU);
    }

}
