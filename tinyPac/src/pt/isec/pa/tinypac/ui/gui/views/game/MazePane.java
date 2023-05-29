package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class MazePane extends BorderPane {
    private ModelManager manager;
    private static final double CELL_WIDTH = 20.0;
    private static final double CELL_HEIGHT = 20.0;
    private ImageView[][] maze;
    private Integer rows;
    private Integer columns;
    public MazePane(ModelManager manager) {

        this.manager = manager;
        this.rows = manager.getMazeRows();
        this.columns = manager.getMazeColumns();

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        initializeGrid();
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> updateState());
        manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> updateState());
    }

    private void initializeGrid(){
        this.maze = new ImageView[rows][columns];
        for(int row = 0; row < rows; row++){
            for(int column = 0; column < columns ; column++){
                ImageView imageView = new ImageView();
                imageView.setY((double) row * CELL_HEIGHT);
                imageView.setX((double) column * CELL_WIDTH);
                imageView.setFitHeight(CELL_HEIGHT);
                imageView.setFitWidth(CELL_WIDTH);
                maze[row][column] = imageView;
                this.getChildren().add(imageView);
            }
        }
    }

    private void updateState(){
        if(manager.getState() == State.PAUSE || manager.getState() == State.GameOver){
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

    private void update() {
        this.setVisible(manager.getProgramState() == ProgramManager.GAME);
    }
}