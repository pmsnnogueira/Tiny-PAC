package pt.isec.pa.tinypac.ui.gui.views.menu;

import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.views.game.GamePane;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class GameRootPane extends BorderPane {
    private ModelManager manager;
    public GameRootPane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        StackPane stackPane = new StackPane(
              new GamePane(manager)
        );
        this.setCenter(stackPane);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> updateState());
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
