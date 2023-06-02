package pt.isec.pa.tinypac.ui.gui.views.menu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.views.game.GamePane;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class GameRootPane extends BorderPane {
    private ModelManager manager;
    private GamePane gamePane;
    public GameRootPane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();

    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        gamePane = null;
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> Platform.runLater(() -> update()));

        setOnKeyPressed(keyEvent -> {
            gamePane.handleKeyPress(keyEvent); // Delegate key event handling to the GamePane
        });
    }

    private void update(){
        if(manager.getProgramState() != ProgramManager.GAME){
            this.setVisible(false);
            return;
        }

        StackPane stackPane = new StackPane(
                gamePane = new GamePane(manager)
        );
        this.setCenter(stackPane);
        stackPane.setAlignment(Pos.CENTER);

        requestFocus();         //KeyEvents inside GamePane
        gamePane.requestFocus();
        this.setVisible(true);
    }
}
