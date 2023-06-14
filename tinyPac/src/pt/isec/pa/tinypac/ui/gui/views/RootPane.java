package pt.isec.pa.tinypac.ui.gui.views;

import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.views.menu.GameRootPane;
import pt.isec.pa.tinypac.ui.gui.views.menu.MainMenuPane;
import pt.isec.pa.tinypac.ui.gui.views.menu.Top5Pane;

public class RootPane extends BorderPane {
    private ModelManager manager;
    BorderPane mainMenuPane, gameRootPane, top5Pane;


    public RootPane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        CSSManager.applyCSS(this,"styles.css");
       StackPane stackPane = new StackPane(
               mainMenuPane = new MainMenuPane(manager),
               gameRootPane = new GameRootPane(manager),
               top5Pane = new Top5Pane(manager)
       );

        this.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("background_MainMenu.jpg"),
                                BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(100,100,true,true,true,false)
                        )
        ));

       this.setCenter(stackPane);
    }

    private void registerHandlers() {



    }

    private void update() {

    }

}