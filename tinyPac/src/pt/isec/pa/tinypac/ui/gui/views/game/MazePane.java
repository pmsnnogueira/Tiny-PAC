package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class MazePane extends VBox {
    private ModelManager manager;
    private static final double CELL_WIDTH = 12.5;
    private static final double CELL_HEIGHT = 12.5;
    private GridPane gridPane;

    public MazePane(ModelManager manager) {

        this.manager = manager;
        this.gridPane = null;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void registerHandlers() {
        //manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> updateState());
        manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> Platform.runLater(() -> updateState()));                 //TESTAR ISTO
        manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> Platform.runLater(() -> updateState()));
    }

    private void initializeImagesGrid(){

        this.gridPane = new GridPane();

        for (int row = 0; row < manager.getMazeRows(); row++) {
            for (int column = 0; column < manager.getMazeColumns(); column++) {

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
    private void removeContentAt(int row, int column) {
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            int rowIndex = GridPane.getRowIndex(gridPane.getChildren().get(i));
            int columnIndex = GridPane.getColumnIndex(gridPane.getChildren().get(i));
            if (rowIndex == row && columnIndex == column) {
                gridPane.getChildren().remove(i);
                break;
            }
        }
    }

    private ImageView getImage(char element){

        String imageName = "";

        if(element == Obstacles.WALL.getSymbol()) {
            imageName = "wall.png";
        }else if(element == Obstacles.BALL.getSymbol()){
            imageName = "ball.png";
        }else if(element == Obstacles.PACMAN.getSymbol()){
            imageName = "pacman.png";
        }else if(element == Obstacles.FRUIT.getSymbol()){
            imageName = "fruit.png";
        } else if(element == Obstacles.POWER.getSymbol()){
            imageName = "power.png";
        } else if(element == Obstacles.WARP.getSymbol()){
            //imageView.setImage(fruitImage);
        }else if(element == Obstacles.PORTAL.getSymbol()){
            //imageView.setImage(fruitImage);
        }else if(element == Obstacles.BLINKY.getSymbol()){
            imageName = "blinky.png";
        }

        return new ImageView(ImageManager.getImage(imageName));
    }

    private void updateState(){
        if(manager.getState() == State.PAUSE || manager.getState() == State.GameOver){
            this.setVisible(false);
            return;
        }
        if(gridPane == null){
            if(gridPane == null) {
                System.out.println("Ola");
                initializeImagesGrid();
            }
        }

        if(gridPane != null){
            for (int row = 0; row < manager.getMazeRows(); row++) {
                for (int column = 0; column < manager.getMazeColumns(); column++) {
                    ImageView imageView = getImageInPosition(row,column);
                    imageView.setFitWidth(CELL_WIDTH);
                    imageView.setFitHeight(CELL_HEIGHT);
                    removeContentAt(row,column);
                    gridPane.add(imageView, column, row);
                }
            }
        }
        this.setVisible(true);
    }

    /*private ImageView getImageInGridPane(int row, int col){

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (ImageView) node;
            }
        }
        return null;
    }*/

    private void update() {
        this.setVisible(manager.getProgramState() == ProgramManager.GAME);
    }
}