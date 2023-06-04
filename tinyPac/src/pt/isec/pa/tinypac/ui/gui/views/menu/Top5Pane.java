package pt.isec.pa.tinypac.ui.gui.views.menu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class Top5Pane extends BorderPane {

    private ToggleButton btnMenu ,btnExitGame;
    private static final Integer BTN_MIN_WIDTH = 100;
    private static final Integer BTN_PREF_WIDTH = 120;
    private static final Integer BTN_MAX_WIDTH = 150;


    private static final Integer BTN_MIN_HEIGHT = 50;
    private static final Integer BTN_PREF_HEIGHT = 75;
    private static final Integer BTN_MAX_HEIGHT = 80;

    private static final Integer BTN_SPACING = 15;

    private VBox top5List;

    private ModelManager manager;

    public Top5Pane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private TilePane getLine(double width, String... strings) {
        TilePane tilePane = new TilePane();
        for (int i = 0; i <strings.length; i++) {
            Label label = new Label(strings[i]);
            tilePane.getChildren().add(label);
        }
        tilePane.setPrefWidth(width);
        tilePane.setPrefTileWidth((width - 20)/strings.length);
        return tilePane;
    }

    private void createViews() {


        Label lbMain = new Label("Tiny-PAC");
        lbMain.getStyleClass().add("mainLabel");
        lbMain.setPadding(new Insets(0,0,25,0));

        TilePane titlePane = getLine(
                450,
                "Username",
                "Score"
        );
        titlePane.setStyle("-fx-background-color: #c0c0c0;");
        top5List = new VBox();
        VBox listWithTitle = new VBox(titlePane,top5List);
        listWithTitle.setBorder(new Border(new BorderStroke(Color.DARKGRAY,BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,new BorderWidths(2))));


        ToggleGroup tgMenuButtons = new ToggleGroup();
        btnMenu = new ToggleButton("Back To Menu");
        btnMenu.setMinHeight(BTN_MIN_HEIGHT);
        btnMenu.setPrefHeight(BTN_PREF_HEIGHT);
        btnMenu.setMaxHeight(BTN_MAX_HEIGHT);
        btnMenu.setMinWidth(BTN_MIN_WIDTH);
        btnMenu.setPrefWidth(BTN_PREF_WIDTH + 20);
        btnMenu.setMaxWidth(BTN_MAX_WIDTH + 20);
        btnMenu.getStyleClass().add("mainButton");
        btnMenu.setSelected(false);

        btnExitGame = new ToggleButton("Exit");
        btnExitGame.setMinHeight(BTN_MIN_HEIGHT);
        btnExitGame.setPrefHeight(BTN_PREF_HEIGHT);
        btnExitGame.setMaxHeight(BTN_MAX_HEIGHT);
        btnExitGame.setMinWidth(BTN_MIN_WIDTH);
        btnExitGame.setPrefWidth(BTN_PREF_WIDTH);
        btnExitGame.setMaxWidth(BTN_MAX_WIDTH);
        btnExitGame.getStyleClass().add("mainButton");
        btnExitGame.setSelected(false);

        tgMenuButtons.getToggles().addAll(btnMenu,btnExitGame);

        HBox menu = new HBox(btnMenu,btnExitGame);
        menu.setSpacing(BTN_SPACING);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(20,0,0,0));


        VBox vBoxList = new VBox(lbMain, listWithTitle , menu);
        vBoxList.setFillWidth(false);
        vBoxList.setSpacing(10);
        vBoxList.setAlignment(Pos.CENTER);

        this.setCenter(vBoxList);
    }

    private void registerHandlers() {

        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> update());

        btnMenu.setOnAction(actionEvent -> {
            manager.changeToMainMenu();
        });

        btnExitGame.setOnAction(actionEvent -> {
            Platform.exit();
        });
    }

    private void update() {
        this.setVisible(manager.getProgramState() == ProgramManager.TOP5);
    }
}
