package pt.isec.pa.tinypac.ui.gui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class Top5Pane extends BorderPane {

    private VBox menu;
    private ToggleGroup tgMenuButtons;

    private ToggleButton btnBackToMenu, btnExitGame;

    private ModelManager manager;

    private static final Integer BTN_PREF_WIDTH = 100;
    public Top5Pane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }



    private void createViews() {

        tgMenuButtons = new ToggleGroup();

        btnBackToMenu = new ToggleButton("Back To Menu");
        btnBackToMenu.setToggleGroup(tgMenuButtons);
        btnBackToMenu.setPrefHeight(100);
        btnBackToMenu.setPrefWidth(BTN_PREF_WIDTH);
        btnBackToMenu.setSelected(false);

        btnExitGame = new ToggleButton("Exit");
        btnExitGame.setToggleGroup(tgMenuButtons);
        btnExitGame.setPrefHeight(100);
        btnExitGame.setPrefWidth(BTN_PREF_WIDTH);
        btnExitGame.setSelected(false);

        menu = new VBox(btnBackToMenu, btnExitGame);
        menu.setMinWidth(250);
        menu.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(menu);
        menu.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> {
            updateState();
        });

        btnBackToMenu.setOnAction(actionEvent -> {

            manager.changeToMainMenu();

            /*MainMenuPane mainMenu = new MainMenuPane();
            ((BorderPane)this.getScene().getRoot()).setCenter(mainMenu);*/

        });

        btnExitGame.setOnAction(actionEvent -> {

            manager.changeToMainMenu();

            /*MainMenuPane mainMenu = new MainMenuPane();
            ((BorderPane)this.getScene().getRoot()).setCenter(mainMenu);*/

        });
    }

    private void updateState(){
        if(manager.getProgramState() != ProgramManager.TOP5) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

    private void update() {
        this.setVisible(manager.getProgramState() == ProgramManager.TOP5);
    }
}
