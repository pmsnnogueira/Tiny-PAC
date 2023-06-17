package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
    private MazePane mazePane;
    public GamePane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();
        requestFocus();
    }

    private void createViews() {

        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Label label = new Label("Esc to pause the game");
        label.getStyleClass().add("Top5BottomLb");
        this.mazePane = new MazePane(manager);

        menu = new VBox(mazePane, label);
        this.setCenter(menu);
        menu.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> update());
        //manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> updateState());
    }

    private void update(){
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