package pt.isec.pa.tinypac.ui.gui.views.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.views.game.GamePane;
import pt.isec.pa.tinypac.utils.ProgramManager;
import pt.isec.pa.tinypac.utils.Stack;

public class GameRootPane extends BorderPane {
    private ModelManager manager;
    public GameRootPane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        StackPane stackPane = new StackPane(
                new GamePane(manager)
        );
        this.setCenter(stackPane);
        stackPane.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> updateState());
    }

    private void updateState(){
        if(manager.getProgramState() != ProgramManager.GAME){
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

    private void update() {
        this.setVisible(manager.getProgramState() == ProgramManager.GAME);
    }
}
