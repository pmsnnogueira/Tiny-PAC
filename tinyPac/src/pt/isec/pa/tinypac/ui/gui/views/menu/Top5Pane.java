package pt.isec.pa.tinypac.ui.gui.views.menu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.views.others.InfoView;
import pt.isec.pa.tinypac.utils.UIManager;

import java.util.ArrayList;
import java.util.List;

public class Top5Pane extends BorderPane {

    private ToggleButton btnMenu;
    private static final Integer BTN_MIN_WIDTH = 100;
    private static final Integer BTN_PREF_WIDTH = 120;
    private static final Integer BTN_MAX_WIDTH = 150;


    private static final Integer BTN_MIN_HEIGHT = 45;
    private static final Integer BTN_PREF_HEIGHT = 50;
    private static final Integer BTN_MAX_HEIGHT = 75;

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
            label.setStyle("-fx-text-fill: #FFFFFF;");
            tilePane.getChildren().add(label);

        }
        tilePane.setPrefWidth(width);
        tilePane.setPrefTileWidth((width - 20)/strings.length);
        return tilePane;
    }

    private void createViews() {


        VBox vBoxLbMain = new VBox();
        Label lbMain = new Label("Tiny-PAC");
        lbMain.getStyleClass().add("mainLabel");
        Label lbTop5 = new Label("Top5");
        lbTop5.getStyleClass().add("mainSubLabel");
        vBoxLbMain.setAlignment(Pos.CENTER);
        vBoxLbMain.getChildren().addAll(lbMain,lbTop5);
        vBoxLbMain.setPadding(new Insets(0,0,25,0));

        TilePane titlePane = getLine(
                450,
                "Username",
                "Score"
        );
        titlePane.setStyle("-fx-background-color: #444444;");
        top5List = new VBox();
        top5List.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,Insets.EMPTY)));
        VBox listWithTitle = new VBox(titlePane,top5List);
        listWithTitle.setBorder(new Border(new BorderStroke(Color.DARKGRAY,BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,new BorderWidths(2))));


        btnMenu = new ToggleButton("Back To Menu");
        btnMenu.setMinHeight(BTN_MIN_HEIGHT);
        btnMenu.setPrefHeight(BTN_PREF_HEIGHT);
        btnMenu.setMaxHeight(BTN_MAX_HEIGHT);
        btnMenu.setMinWidth(BTN_MIN_WIDTH);
        btnMenu.setPrefWidth(BTN_PREF_WIDTH + 20);
        btnMenu.setMaxWidth(BTN_MAX_WIDTH + 20);
        btnMenu.getStyleClass().add("mainButton");
        btnMenu.setSelected(false);


        HBox menu = new HBox(btnMenu);
        menu.setSpacing(BTN_SPACING);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(20,0,0,0));


        VBox vBoxList = new VBox(vBoxLbMain, listWithTitle , menu);
        vBoxList.setFillWidth(false);
        vBoxList.setSpacing(10);
        vBoxList.setAlignment(Pos.CENTER);

        this.setCenter(vBoxList);

        InfoView info = new InfoView();
        HBox hBox = new HBox(info);
        hBox.setAlignment(Pos.CENTER);


        this.setBottom(hBox);
    }

    private void registerHandlers() {

        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> update());

        btnMenu.setOnAction(actionEvent -> {
            manager.changeToMainMenu();
        });


    }

    private void update() {
        this.setVisible(manager.getProgramState() == UIManager.TOP5);


        List<String> top5 = new ArrayList<>();
        top5.add("Pedro Nogueira");
        top5.add("Pedro Nogueira");
        top5.add("Pedro Nogueira");
        top5.add("Pedro Nogueira");
        top5.add("Pedro Nogueira");
        top5List.getChildren().clear();

        for (int i = 0; i < top5.size(); i++) {
            TilePane tilePane = getLine(
                    450,
                    ""+top5.get(i),
                    ""+ 500
            );
            tilePane.setStyle("-fx-background-color: #000000;");

            top5List.getChildren().add(tilePane);
        }
    }
}
