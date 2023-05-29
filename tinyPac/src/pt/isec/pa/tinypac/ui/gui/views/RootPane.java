package pt.isec.pa.tinypac.ui.gui.views;

import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.ModelManager;

public class RootPane extends BorderPane {
    private ModelManager manager;

    public RootPane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

       StackPane stackPane = new StackPane(
               new MainMenuPane(manager),
               new Top5Pane(manager)



       );
       this.setCenter(stackPane);
    }

    private void registerHandlers() {

    }

    private void update() {

    }

}