package pt.isec.pa.tinypac.ui.gui.views.menu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.resources.FontManager;
import pt.isec.pa.tinypac.utils.UIManager;

public class MainMenuPane extends BorderPane {
    private ToggleButton btnPlayGame, btnTop5 ,btnExitGame;
    private static final Integer BTN_MIN_WIDTH = 100;
    private static final Integer BTN_PREF_WIDTH = 120;
    private static final Integer BTN_MAX_WIDTH = 150;


    private static final Integer BTN_MIN_HEIGHT = 50;
    private static final Integer BTN_PREF_HEIGHT = 75;
    private static final Integer BTN_MAX_HEIGHT = 80;

    private static final Integer BTN_SPACING = 15;
    private ModelManager manager;

    ToggleButton btnNo, btnYes;

    public MainMenuPane(ModelManager manager) {

        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        Label lbMain = new Label("Tiny-PAC");
        lbMain.setFont(FontManager.getFont("joystixmonospace.ttf"));
        lbMain.getStyleClass().add("mainLabel");
        lbMain.setPadding(new Insets(0,0,25,0));

        ToggleGroup tgMenuButtons = new ToggleGroup();
        btnPlayGame = new ToggleButton("Play Game");
        btnPlayGame.setMinHeight(BTN_MIN_HEIGHT);
        btnPlayGame.setPrefHeight(BTN_PREF_HEIGHT);
        btnPlayGame.setMaxHeight(BTN_MAX_HEIGHT);
        btnPlayGame.setMinWidth(BTN_MIN_WIDTH);
        btnPlayGame.setPrefWidth(BTN_PREF_WIDTH);
        btnPlayGame.setMaxWidth(BTN_MAX_WIDTH);
        btnPlayGame.getStyleClass().add("mainButton");
        btnPlayGame.setSelected(false);

        btnTop5 = new ToggleButton("Top 5");
        btnTop5.setMinHeight(BTN_MIN_HEIGHT);
        btnTop5.setPrefHeight(BTN_PREF_HEIGHT);
        btnTop5.setMaxHeight(BTN_MAX_HEIGHT);
        btnTop5.setMinWidth(BTN_MIN_WIDTH);
        btnTop5.setPrefWidth(BTN_PREF_WIDTH);
        btnTop5.setMaxWidth(BTN_MAX_WIDTH);
        btnTop5.getStyleClass().add("mainButton");
        btnTop5.setSelected(false);

        btnExitGame = new ToggleButton("Exit");
        btnExitGame.setMinHeight(BTN_MIN_HEIGHT);
        btnExitGame.setPrefHeight(BTN_PREF_HEIGHT);
        btnExitGame.setMaxHeight(BTN_MAX_HEIGHT);
        btnExitGame.setMinWidth(BTN_MIN_WIDTH);
        btnExitGame.setPrefWidth(BTN_PREF_WIDTH);
        btnExitGame.setMaxWidth(BTN_MAX_WIDTH);
        btnExitGame.getStyleClass().add("mainButton");
        btnExitGame.setSelected(false);

        tgMenuButtons.getToggles().addAll(btnPlayGame, btnTop5,btnExitGame);

        VBox menu = new VBox(lbMain,btnPlayGame, btnTop5,btnExitGame);
        menu.setSpacing(BTN_SPACING);
        menu.setAlignment(Pos.CENTER);
        this.setCenter(menu);


    }

    private void createPopUpLoadGame(){
        if(manager.checkIfSavedGamesExist()){
            //Create PopUpMenu
            Stage dlg = new Stage();
            Label label = new Label("Do you want to load the saved Game?");
            btnNo = new ToggleButton("No");
            btnYes = new ToggleButton("Yes");

            btnNo.setOnAction(e -> {
                manager.changeToGame();
                dlg.close();
            });

            btnYes.setOnAction(e -> {
                manager.loadSavedGame();
                manager.changeToGame();
                dlg.close();
            });

            VBox vBox = new VBox();
            HBox hbButtons = new HBox(btnNo,btnYes);
            hbButtons.setSpacing(2.5);
            hbButtons.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(label,hbButtons);

            vBox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vBox,300,70);
            dlg.setScene(scene);
            dlg.setTitle("Load Game");
            dlg.initModality(Modality.APPLICATION_MODAL);
            dlg.initOwner(this.getScene().getWindow());
            dlg.showAndWait();
            dlg.setAlwaysOnTop(true);
        }
    }

    private void registerHandlers() {

        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> update());

        btnPlayGame.setOnAction(actionEvent -> {
            manager.initGame();
            createPopUpLoadGame();
        });

        btnTop5.setOnAction(actionEvent -> {
            manager.changeToTop5();
        });

        btnExitGame.setOnAction(actionEvent -> {
            Platform.exit();
        });

    }

    private void update() {
        this.setVisible(manager.getProgramState() == UIManager.MAIN_MENU);
    }

}
