package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.resources.SoundManager;
import pt.isec.pa.tinypac.utils.UIManager;

public class GameOverUI extends BorderPane {
    private ModelManager manager;

    private ToggleButton btnBackToMainMenu;
    private ToggleButton btnExit;

    private Label lbPoints;

    private static final Integer MENU_MIN_WIDTH = 150;

    private static final Integer BTN_MIN_WIDTH = 170;
    private static final Integer BTN_PREF_WIDTH = 180;
    private static final Integer BTN_MAX_WIDTH = 200;


    private static final Integer BTN_MIN_HEIGHT = 25;
    private static final Integer BTN_PREF_HEIGHT = 50;
    private static final Integer BTN_MAX_HEIGHT = 50;
    private static final Integer BTN_SPACING = 20;

    public GameOverUI(ModelManager manager){
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
        requestFocus();
    }

    private void createViews() {

        this.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("background_Gameover.jpg"),
                                BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,true,false)
                        )
        ));

        //Menu
        VBox vBox = new VBox();
        lbPoints = new Label("Score");

        lbPoints.getStyleClass().add("mainLabel");
        lbPoints.setAlignment(Pos.CENTER);

        //Buttons
        ToggleGroup tgMenuButtons = new ToggleGroup();
        btnBackToMainMenu = new ToggleButton("Back To Main Menu");
        btnBackToMainMenu.setToggleGroup(tgMenuButtons);
        btnBackToMainMenu.setMinWidth(BTN_MIN_WIDTH);
        btnBackToMainMenu.setPrefWidth(BTN_PREF_WIDTH);
        btnBackToMainMenu.setMaxWidth(BTN_MAX_WIDTH);
        btnBackToMainMenu.setPrefHeight(BTN_PREF_HEIGHT);
        btnBackToMainMenu.setMinHeight(BTN_MIN_HEIGHT);
        btnBackToMainMenu.setMaxHeight(BTN_MAX_HEIGHT);

        btnBackToMainMenu.getStyleClass().add("gameOverButton");
        btnBackToMainMenu.setSelected(false);

        btnExit = new ToggleButton("Exit Game");
        btnExit.setToggleGroup(tgMenuButtons);
        btnExit.setMinWidth(BTN_MIN_WIDTH);
        btnExit.setPrefWidth(BTN_PREF_WIDTH);
        btnExit.setMaxWidth(BTN_MAX_WIDTH);
        btnExit.setPrefHeight(BTN_PREF_HEIGHT);
        btnExit.setMinHeight(BTN_MIN_HEIGHT);
        btnExit.setMaxHeight(BTN_MAX_HEIGHT);
        btnExit.getStyleClass().add("gameOverButton");
        btnExit.setSelected(false);

        HBox hBoxButtons = new HBox(btnBackToMainMenu,btnExit);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(BTN_SPACING);

        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(50,0,0,15));
        vBox.setSpacing(5);

        vBox.getChildren().addAll(lbPoints,hBoxButtons);
        this.setCenter(vBox);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> Platform.runLater(()->update()));
        //manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> updateState());

        btnBackToMainMenu.setOnAction(actionEvent -> {
            manager.changeToMainMenu();
        });

        btnExit.setOnAction(actionEvent -> {
            Platform.exit();
        });
    }

    private void update(){
        if(manager.getProgramState() != UIManager.GAME || manager.getState() != State.GameOver){
            this.setVisible(false);
            return;
        }

        lbPoints.setText("Score " + manager.getScore());
        this.setVisible(true);

        if(manager.isInTop5()){


            //Create PopUpMenu
            Stage dlg = new Stage();
            Label label = new Label("You are in Top 5\n");
            Label labelUsername = new Label("Insert your name: ");
            labelUsername.setPadding(new Insets(0,0,10,0));
            TextField textField = new TextField();


            ToggleButton ok = new ToggleButton("Ok");
            ok.setPadding(new Insets(10,0,10,0));
            ok.setAlignment(Pos.CENTER);
            HBox hBox = new HBox(labelUsername, textField);

            ok.setOnAction(actionEvent -> {
                if(textField.getText().equals("")){

                }else{
                    manager.addIntoTop5(textField.getText());
                    dlg.close();
                }
            });

            VBox vBox = new VBox();
            HBox hbButtons = new HBox(label, hBox);
            hbButtons.setSpacing(2.5);
            hbButtons.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(label,hbButtons,ok);

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
}
