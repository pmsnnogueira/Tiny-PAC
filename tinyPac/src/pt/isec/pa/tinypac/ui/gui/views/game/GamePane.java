package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.ui.gui.resources.SoundManager;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.UIManager;

public class GamePane extends BorderPane {

    private ModelManager manager;
    private VBox menu;
    private ToggleGroup tgMenuButtons;
    private ToggleButton btnChangeDirection;
    private MazePane mazePane;
    private static final Integer MENU_MIN_WIDTH = 150;
    private static final Integer BTN_PREF_WIDTH = 150;
    private static final Integer BTN_MAX_WIDTH = 200;
    private static final Integer BTN_PREF_HEIGHT = 100;
    private static final Integer BTN_SPACING = 5;
    public GamePane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();
        requestFocus();
    }

    private void createViews() {

        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        tgMenuButtons = new ToggleGroup();

        this.mazePane = new MazePane(manager);

        btnChangeDirection = new ToggleButton("Change Pacman Direction");
        btnChangeDirection.setToggleGroup(tgMenuButtons);
        btnChangeDirection.setPrefHeight(BTN_PREF_HEIGHT);
        btnChangeDirection.setPrefWidth(BTN_PREF_WIDTH);
        btnChangeDirection.setMaxWidth(BTN_MAX_WIDTH);
        btnChangeDirection.setSelected(false);

        menu = new VBox(mazePane/*,btnChangeDirection*/);
       // menu = new VBox(btnChangeDirection);
        //menu.setMinWidth(1000);
        //menu.setMinHeight(Integer.MAX_VALUE);
        this.setCenter(menu);
        menu.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> update());
        //manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> updateState());
    }

    private void update(){
        if(manager.getState() == State.GameOver)
            SoundManager.play("pacman_death.mp3");

        if(manager.getProgramState() != UIManager.GAME || manager.getState() == State.PAUSE || manager.getState() == State.GameOver){
            this.setVisible(false);
            return;
        }

        this.setVisible(true);
    }

    public void handleKeyPress(KeyEvent keyEvent) {

        if(keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W)
            manager.changeDirection(Direction.UP);

        if(keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D)
            manager.changeDirection(Direction.RIGHT);

        if(keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A)
            manager.changeDirection(Direction.LEFT);

        if(keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S)
            manager.changeDirection(Direction.DOWN);

        if(keyEvent.getCode() == KeyCode.ESCAPE)
            manager.changeToPause();

    }
}