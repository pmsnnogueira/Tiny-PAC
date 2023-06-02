package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.ProgramManager;

public class MazePane extends VBox {
    private ModelManager manager;
    private static final double CELL_WIDTH = 12.5;
    private static final double CELL_HEIGHT = 12.5;
    private TilePane tilePane;
    private ImageView[] images;

    public MazePane(ModelManager manager) {

        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        initializeImagesGrid();
    }

    private void registerHandlers() {
        //manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> updateState());
        manager.addPropertyChangeListener(ModelManager.PROP_GAME, evt -> Platform.runLater(() -> update()));                 //TESTAR ISTO
        //manager.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> Platform.runLater(() -> updateState()));

    }

    private void initializeImagesGrid(){

       tilePane = new TilePane(Orientation.HORIZONTAL);

       tilePane.setPrefColumns(manager.getMazeColumns());
       tilePane.setPrefTileHeight(CELL_HEIGHT);
       tilePane.setPrefTileWidth(CELL_WIDTH);

       FlowPane flowPane = new FlowPane(tilePane);
       flowPane.setAlignment(Pos.CENTER);
       AnchorPane.setTopAnchor(flowPane,0.0);
       AnchorPane.setBottomAnchor(flowPane,0.0);
       AnchorPane.setLeftAnchor(flowPane,0.0);
       AnchorPane.setRightAnchor(flowPane,0.0);

       images = new ImageView[manager.getMazeRows() * manager.getMazeColumns()];
       for(int i = 0 ;i < images.length; i++) {

           ImageView imageView = getImageInPosition((i / manager.getMazeColumns()),(i % manager.getMazeColumns()));
           imageView.setFitWidth(CELL_WIDTH);
           imageView.setFitHeight(CELL_HEIGHT);
           images[i] = imageView;
           images[i].setUserData(i);
           tilePane.getChildren().add(images[i]);
        }
       this.getChildren().addAll(flowPane);
    }
    private ImageView getImageInPosition(Integer row, Integer column){
        char element = manager.receiveElement(row,column);
        return getImage(element);
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

    private void update(){

        if(manager.getState() == State.PAUSE || manager.getState() == State.GameOver){
            this.setVisible(false);
            return;
        }

        this.setVisible(true);
    }
}