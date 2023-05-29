package pt.isec.pa.tinypac.ui.gui.views;

import javafx.scene.layout.*;
import pt.isec.pa.tinypac.ui.gui.Manager;

public class RootPane extends BorderPane {
    private Manager manager;

    public RootPane(Manager manager) {

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