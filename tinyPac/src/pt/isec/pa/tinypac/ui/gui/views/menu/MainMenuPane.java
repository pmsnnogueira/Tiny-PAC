package pt.isec.pa.tinypac.ui.gui.views.menu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.ui.gui.views.RootPane;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class MainMenuPane extends BorderPane {

    private VBox menu;
    private ToggleGroup tgMenuButtons;

    private GameRootPane gameRootPane;

    private ToggleButton btnPlayGame, btnTop5 ,btnExitGame;

    private static final Integer MENU_MIN_WIDTH = 150;
    private static final Integer BTN_PREF_WIDTH = 150;
    private static final Integer BTN_MAX_WIDTH = 200;
    private static final Integer BTN_PREF_HEIGHT = 100;
    private static final Integer BTN_SPACING = 5;
    private ModelManager manager;

    private Boolean inGame;

    public MainMenuPane(ModelManager manager) {

        this.manager = manager;
        this.inGame = false;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        tgMenuButtons = new ToggleGroup();

        btnPlayGame = new ToggleButton("Play Game");
        btnPlayGame.setToggleGroup(tgMenuButtons);
        btnPlayGame.setPrefHeight(BTN_PREF_HEIGHT);
        btnPlayGame.setPrefWidth(BTN_PREF_WIDTH);
        btnPlayGame.setMaxWidth(BTN_MAX_WIDTH);
        btnPlayGame.setSelected(false);

        btnTop5 = new ToggleButton("Top 5");
        btnTop5.setToggleGroup(tgMenuButtons);
        btnTop5.setPrefHeight(BTN_PREF_HEIGHT);
        btnTop5.setPrefWidth(BTN_PREF_WIDTH);
        btnTop5.setMaxWidth(BTN_MAX_WIDTH);
        btnTop5.setSelected(false);

        btnExitGame = new ToggleButton("Exit");
        btnExitGame.setToggleGroup(tgMenuButtons);
        btnExitGame.setPrefHeight(BTN_PREF_HEIGHT);
        btnExitGame.setPrefWidth(BTN_PREF_WIDTH);
        btnExitGame.setMaxWidth(BTN_MAX_WIDTH);
        btnExitGame.setSelected(false);

        menu = new VBox(btnPlayGame, btnTop5,btnExitGame);
        menu.setSpacing(BTN_SPACING);
        menu.setMinWidth(MENU_MIN_WIDTH);
        menu.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(menu);
        menu.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {

        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> updateState());

        btnPlayGame.setOnAction(actionEvent -> {
            //GamePane gamePane = new GamePane();
            //((BorderPane)this.getScene().getRoot()).setCenter(gamePane);
            manager.changeToGame();

        });

        btnTop5.setOnAction(actionEvent -> {

            //Top5Pane top5 = new Top5Pane(manager);
            manager.changeToTop5();
            //((RootPane)this.getScene().getRoot()).setCenter(top5);
        });

        btnExitGame.setOnAction(actionEvent -> {
            Platform.exit();
        });

    }

    private void updateState(){
        if(manager.getProgramState() != ProgramManager.MAIN_MENU){
            this.setVisible(false);
            if(manager.getProgramState() == ProgramManager.GAME){
                if(gameRootPane == null){
                    Platform.runLater(()-> {
                        if(gameRootPane == null) {
                            gameRootPane = new GameRootPane(manager);
                            ((RootPane) this.getScene().getRoot()).getChildren().add(gameRootPane);
                            System.out.println("Ola ");
                        }
                    });
                }
            }
            return;
        }

        this.setVisible(true);
    }

    private void update() {
        this.setVisible(manager.getProgramState() == ProgramManager.MAIN_MENU);
    }

}
