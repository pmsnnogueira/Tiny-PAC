package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.ui.gui.views.RootPane;

public class MainJFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        configureStage(stage);
    }

    private void configureStage(Stage stage) {

        RootPane root = new RootPane();
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.setTitle("Tiny-PAC");
        stage.show();
    }

}
