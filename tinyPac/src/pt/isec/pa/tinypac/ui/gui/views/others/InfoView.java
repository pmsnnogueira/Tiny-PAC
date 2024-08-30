package pt.isec.pa.tinypac.ui.gui.views.others;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class InfoView extends Pane {

    public InfoView() {
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        //Student
        HBox hBox = new HBox();
        Label lbIsec = new Label("DEIS-ISEC-IPC");
        lbIsec.getStyleClass().add("Top5BottomLb");
        //lbIsec.setAlignment(Pos.BOTTOM_LEFT);
        ImageView imageView = new ImageView(ImageManager.getImage("isec_logo.png"));

        Label lbStudent = new Label("Pedro Nogueira\na2020136533\nLEI - PA - 22/23\nAcademic assignment");
        lbStudent.getStyleClass().add("Top5BottomLb");
        lbStudent.setAlignment(Pos.BOTTOM_RIGHT);
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);

        hBox.getChildren().addAll(lbIsec,imageView,lbStudent);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(80);
        hBox.setPadding(new Insets(0,0,8,0));

        this.getChildren().add(hBox);
    }

    private void registerHandlers() {


    }

    private void update() {

    }
}
