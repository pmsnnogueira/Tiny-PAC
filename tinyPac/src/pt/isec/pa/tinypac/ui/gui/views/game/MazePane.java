package pt.isec.pa.tinypac.ui.gui.views.game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.UIManager;

public class MazePane extends VBox {
    private ModelManager manager;
    private static final double CELL_WIDTH = 12.5;
    private static final double CELL_HEIGHT = 12.5;
    private TilePane tilePane;
    private ImageView[] images;
    private Label lbGameInfo;

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

    private void updateMazeView(){

        for(int i = 0 ;i < images.length; i++) {

            Image image = getImageViewInPosition((i / manager.getMazeColumns()),(i % manager.getMazeColumns()));
            images[i].setImage(image);
        }
    }

    private void initializeImagesGrid(){

        lbGameInfo = new Label();
        lbGameInfo.getStyleClass().add("gameInfoLabel");
        lbGameInfo.setPadding(new Insets(0,0,5,0));
        lbGameInfo.setAlignment(Pos.CENTER);
        this.getChildren().add(lbGameInfo);

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

           ImageView imageView = new ImageView(getImageViewInPosition((i / manager.getMazeColumns()),(i % manager.getMazeColumns())));
           imageView.setFitWidth(CELL_WIDTH);
           imageView.setFitHeight(CELL_HEIGHT);
           images[i] = imageView;
           images[i].setUserData(i);
           tilePane.getChildren().add(images[i]);
        }

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(flowPane);
    }



    private Image getImageViewInPosition(Integer row, Integer column){
        char element = manager.receiveElement(row,column);
        return getImage(element, column, row);
    }



    private Image getImage(char element, int posX, int posY){

        String imageName = "";

        if(element == Obstacles.WALL.getSymbol()) {
            imageName = "wall.png";
        }else if(element == Obstacles.BALL.getSymbol()){
            imageName = "ball.png";
        }else if(element == Obstacles.PACMAN.getSymbol()){
           if(manager.getDirection() == null)
                imageName = "pacman_left.png";
           else {
               switch (manager.getDirection()) {
                   case UP -> imageName = "pacman_up.png";
                   case RIGHT -> imageName = "pacman_right.png";
                   case LEFT -> imageName = "pacman_left.png";
                   case DOWN -> imageName = "pacman_down.png";
                   default -> imageName = "pacman_up.png";
               }
           }
        } else if(element == Obstacles.FRUIT.getSymbol()){
            imageName = "fruit.png";
        } else if(element == Obstacles.POWER.getSymbol()){
            imageName = "power.png";
        } else if(element == Obstacles.WARP.getSymbol()){
            imageName = "portal.png";
        }else if(element == Obstacles.PORTAL.getSymbol()){
            imageName = "ghostPortal.png";
        }

        if(manager.charIsGhosts(element))
            switch (manager.getState()) {
                case WAIT_FOR_DIRECTIONS, LOCKED_GHOSTS, GAME -> {
                    imageName = chooseImageForGhosts(element);
                }
                case GHOST_VULNERABLE -> {
                    if(!manager.isVulnerableGhostPosition(posX, posY)) {
                            imageName = chooseImageForGhosts(element);
                    }else{
                        if(manager.isGhostDead(posX, posY))
                            imageName = "pacmanDeath.png";
                        else
                            imageName = "vulnerableGhost.gif";
                    }
                }
            }
        return ImageManager.getImage(imageName);
    }

    private String chooseImageForGhosts(char element){

        String imageName = "";

        if (element == Obstacles.BLINKY.getSymbol()) {
            imageName = "redGhost.gif";
        } else if (element == Obstacles.PINKY.getSymbol()) {
            imageName = "pinkGhost.gif";
        } else if (element == Obstacles.CLYDE.getSymbol()) {
            imageName = "yellowGhost.gif";
        } else if (element == Obstacles.INKY.getSymbol()) {
            imageName = "blueGhost.gif";
        }

        return imageName;
    }

    private void update(){

        if(manager.getProgramState() != UIManager.GAME || manager.getState() == State.PAUSE || manager.getState() == State.GameOver){
            this.setVisible(false);
            return;
        }

        lbGameInfo.setText(manager.showGameInfo());
        updateMazeView();

        this.setVisible(true);
    }
}