package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.ui.gui.views.RootPane;

public class MainJFX extends Application {

    private Manager manager;
    @Override
    public void start(Stage stage) throws Exception {
        this.manager = new Manager();
        configureStage(stage);
    }

    private void configureStage(Stage stage) {
        newStage(stage, "Tiny-PAC");

        Stage stage2 = new Stage();
        newStage(stage2, "Tiny-PAC2");

        stage.setOnCloseRequest(windowEvent -> stage2.close());
        stage2.setOnCloseRequest(windowEvent -> stage.close());

    }

    private void newStage(Stage stage, String title){
        RootPane root = new RootPane(manager);
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

}
