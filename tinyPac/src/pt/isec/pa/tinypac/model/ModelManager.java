package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.UIManager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelManager {

    public static final String PROP_MENU = "_menu_";
    public static final String PROP_DATA = "_data_";
    public static final String PROP_GAME = "_gameMenu_";

    private static final Integer GAME_ENGINE_TIME = 100;

    private GameEngine gameEngine;
    private Context context;

    private UIManager UIManager;
    private PropertyChangeSupport pcs;

    public ModelManager(){
        //this.gameEngine = new GameEngine();
        //this.context = new Context();
        this.context = null;
        //this.gameEngine.registerClient(this);

        this.UIManager = UIManager.MAIN_MENU;
        this.pcs = new PropertyChangeSupport(this);
    }


    public State getState(){
        return context.getState();
    }

    public boolean changeDirection(Direction direction){
        if(context.changeDirection(direction)) {
            System.out.println("New Direction: " + direction.toString());
            pcs.firePropertyChange(PROP_GAME, null,null);
            return true;
        }
        return false;
    }

    public boolean pause(){
        gameEngine.pause();
        return context.pause(gameEngine.getInterval());
    }
    public boolean resume(){
        gameEngine.resume();
        return context.resume();
    }

    public char[][] showMaze(){
        return context.showMaze();
    }

    public Integer getMazeRows(){
        return context.getMazeRows();
    }

    public Integer getMazeColumns(){
        return context.getMazeColumns();
    }
    public String showGameInfo(){
        return context.showGameInfo();
    }

    public void evolve(long currentTime) {
        if(context.evolve(currentTime)) {
            pcs.firePropertyChange(PROP_GAME, null, null);
            System.out.println("Fire");
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(property,listener);
    }


    public UIManager getProgramState(){
        return UIManager;
    }

    public void changeToTop5(){
        this.UIManager = UIManager.TOP5;
        pcs.firePropertyChange(PROP_MENU,null,null);
    }

    public void initGame(){
        if(context == null)
            this.context = new Context();
        if(gameEngine == null) {
            this.gameEngine = new GameEngine();
            this.gameEngine.registerClient((gameEngine,currentTime) -> evolve(currentTime));
        }
    }

    public void changeToGame(){
        gameEngine.start(GAME_ENGINE_TIME);
        this.UIManager = UIManager.GAME;
        pcs.firePropertyChange(PROP_GAME,null,null);
    }

    public void changeToMainMenu(){
        if(UIManager == UIManager.GAME){
            gameEngine.stop();
            gameEngine = null;
            context = null;
        }
        this.UIManager = UIManager.MAIN_MENU;
        pcs.firePropertyChange(PROP_GAME,null,null);
        pcs.firePropertyChange(PROP_MENU,null,null);
    }

    public char receiveElement(int row, int column) {
        return context.receiveElement(row,column);
    }

    public Direction getDirection() {
        return context.getDirection();
    }

    public boolean charIsGhosts(char c) {
        return context.charIsGhost(c);
    }

    public int getScore() {
        return context.getScore();
    }

    public void changeToPause() {
        pause();
        pcs.firePropertyChange(PROP_GAME,null,null);
    }

    public void changeToResume() {
        resume();
        pcs.firePropertyChange(PROP_GAME,null,null);
    }

    public void changeToSaveAndExit() {

        context.saveGame();
        pcs.firePropertyChange(PROP_GAME,null,null);
    }

    public boolean checkIfSavedGamesExist() {
        return context.checkIfSavedGamesExist();
    }

    public void loadSavedGame() {
        context.loadSavedGame();
    }
}