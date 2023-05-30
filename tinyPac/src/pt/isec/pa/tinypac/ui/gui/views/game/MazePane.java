package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class MazePane extends VBox {
    private ModelManager manager;
    private static final double CELL_WIDTH = 12.5;
    private static final double CELL_HEIGHT = 12.5;
    private GridPane gridPane;
    private Integer rows;
    private Integer columns;
    private Image wallImage;
    private Image ballImage;
    private Image fruitImage;
    private Image pacmanImage;
    private Image powerImage;
    public MazePane(ModelManager manager) {

        this.manager = manager;
        this.rows = 0;
        this.columns = 0;
        this.gridPane = null;

        this.wallImage = new Image(getClass().getResourceAsStream("/pt/isec/pa/tinypac/ui/gui/resources/wall.png"));

        this.ballImage = new Image(getClass().getResourceAsStream("/pt/isec/pa/tinypac/ui/gui/resources/ball.png"));
        this.fruitImage = new Image(getClass().getResourceAsStream("/pt/isec/pa/tinypac/ui/gui/resources/fruit.png"));
        this.pacmanImage = new Image(getClass().getResourceAsStream("/pt/isec/pa/tinypac/ui/gui/resources/pacman.png"));
        this.powerImage= new Image(getClass().getResourceAsStream("/pt/isec/pa/tinypac/ui/gui/resources/power.png"));

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(ModelManager.PROP_MENU, evt -> updateState());
        manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> updateState());
    }

    private void initializeImagesGrid(){

        this.rows = manager.getMazeRows();
        this.columns = manager.getMazeColumns();
        this.gridPane = new GridPane();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                ImageView imageView = getImageInPosition(row,column);
                imageView.setFitWidth(CELL_WIDTH);
                imageView.setFitHeight(CELL_HEIGHT);

                gridPane.add(imageView, column, row);
            }
        }

        gridPane.setVgap(2);
        gridPane.setHgap(2);
        this.getChildren().add(gridPane);
    }

    private ImageView getImageInPosition(Integer row, Integer column){
        char element = manager.receiveElement(row,column);
        return getImage(element);
    }

    private ImageView getImage(char element){

        ImageView imageView = new ImageView();

        if(element == Obstacles.WALL.getSymbol()) {
            imageView.setImage(wallImage);
        }else if(element == Obstacles.BALL.getSymbol()){
            imageView.setImage(ballImage);
        }else if(element == Obstacles.PACMAN.getSymbol()){
            imageView.setImage(pacmanImage);
        }else if(element == Obstacles.FRUIT.getSymbol()){
            imageView.setImage(fruitImage);
        } else if(element == Obstacles.POWER.getSymbol()){
            imageView.setImage(powerImage);
        } else if(element == Obstacles.WARP.getSymbol()){
            //imageView.setImage(fruitImage);
        }else if(element == Obstacles.PORTAL.getSymbol()){
            //imageView.setImage(fruitImage);
        }

        return imageView;
    }

    private void updateState(){
        if(manager.getState() == State.PAUSE || manager.getState() == State.GameOver){
            this.setVisible(false);
            return;
        }
        if(gridPane == null){
            Platform.runLater(()->{
                if(gridPane == null) {
                    initializeImagesGrid();
                }
            });
        }
        this.setVisible(true);
    }

    private void update() {
        this.setVisible(manager.getProgramState() == ProgramManager.GAME);
    }
}