package pt.isec.pa.tinypac.ui.gui.views;

import javafx.scene.layout.*;

public class RootPane extends BorderPane {

    private MainMenuPane mainMenu;

    public RootPane() {

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

       StackPane stackPane = new StackPane(
                mainMenu = new MainMenuPane()
       );
       this.setCenter(stackPane);
    }

    private void registerHandlers() {

    }

    private void update() {

    }

}