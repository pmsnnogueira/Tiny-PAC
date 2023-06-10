package pt.isec.pa.tinypac.ui.gui.views.menu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.views.game.GameOverUI;
import pt.isec.pa.tinypac.ui.gui.views.game.GamePane;
import pt.isec.pa.tinypac.ui.gui.views.game.PauseUI;
import pt.isec.pa.tinypac.utils.UIManager;

public class GameRootPane extends BorderPane {
    private ModelManager manager;
    private GamePane gamePane;
    private PauseUI pauseUI;
    private GameOverUI gameOverUI;
    public GameRootPane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();

    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> Platform.runLater(() -> update()));

        setOnKeyPressed(keyEvent -> {
            gamePane.handleKeyPress(keyEvent); // Delegate key event handling to the GamePane
        });
    }

    private void update(){
        if(manager.getProgramState() != UIManager.GAME){
            this.setVisible(false);
            return;
        }


        StackPane stackPane = new StackPane(
                gamePane = new GamePane(manager),
                pauseUI = new PauseUI(manager),
                gameOverUI = new GameOverUI(manager)
        );
        this.setCenter(stackPane);
        stackPane.setAlignment(Pos.CENTER);

        requestFocus();         //KeyEvents inside GamePane
        gamePane.requestFocus();
        this.setVisible(true);
    }
}
