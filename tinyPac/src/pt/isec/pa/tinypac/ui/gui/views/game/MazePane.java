package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class MazePane extends BorderPane {
    private ModelManager manager;
    private static final double CELL_WIDTH = 12.5;
    private static final double CELL_HEIGHT = 12.5;
    private GridPane gridPane;
    private Integer rows;
    private Integer columns;

    private Image wallImage;
    private Image ballImage;
    public MazePane(ModelManager manager) {

        this.manager = manager;
        this.rows = manager.getMazeRows();
        this.columns = manager.getMazeColumns();

        this.wallImage = new Image(getClass().getResourceAsStream("/pt/isec/pa/tinypac/ui/gui/resources/wall.png"));

        this.ballImage = new Image(getClass().getResourceAsStream("/pt/isec/pa/tinypac/ui/gui/resources/whitedot.png"));

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        initializeImagesGrid();
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> updateState());
        manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> updateState());
    }

    private void initializeImagesGrid(){

        this.gridPane = new GridPane();
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                ImageView imageView = new ImageView();
                char element = manager.receiveElement(row,column);
                if(element == Obstacles.WALL.getSymbol()) {
                    imageView.setImage(wallImage);
                    //this.gridPane.add(imageView, column, row);
                }else if(element == Obstacles.BALL.getSymbol()){
                    imageView.setImage(ballImage);
                }
                else
                    continue;

                imageView.setFitWidth(CELL_WIDTH);
                imageView.setFitHeight(CELL_HEIGHT);

                gridPane.add(imageView, column, row);
            }
        }

        gridPane.setVgap(2);
        gridPane.setHgap(2);
        this.getChildren().add(gridPane);
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

        for(int row = 0; row < rows; row++){
            for(int column = 0; column < columns ; column++){
             /*   if(Math.random()  > 0.5)
                    this.gridPane.add(new ImageView(wallImage), column,row);
                else
                    this.gridPane.add(new ImageView(ballImage), column,row);*/
            }
        }
    }
}