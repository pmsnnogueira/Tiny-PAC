package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.data.Top5;
import pt.isec.pa.tinypac.model.data.Top5Data;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.GameStatus;
import pt.isec.pa.tinypac.utils.UIManager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * The ModelManager class manages the game Model and controls the gameFlow
 * It includes methods for interacting with the game Engine,
 * managing game states, and firing property change events
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class ModelManager {

    public static final String PROP_MENU = "_menu_";
    public static final String PROP_GAME = "_gameMenu_";
    public static final String PROP_BOARD = "_gameBoard_";

    private static final Integer GAME_ENGINE_TIME = 100;

    private GameEngine gameEngine;
    private Context context;

    private UIManager UIManager;
    private PropertyChangeSupport pcs;

    /**
     * Construct a modelManager Object
     * Initializes the context,the UIState, and property change support.
     */
    public ModelManager(){
        this.context = null;
        this.UIManager = UIManager.MAIN_MENU;
        this.pcs = new PropertyChangeSupport(this);
    }


    /**
     * The current Game is returned
     * @return The current fsm state of the game
     */
    public State getState(){
        return context.getState();
    }

    /**
     * Changes the direction of the pacman direction
     * @param direction The new Direction to change the pacman direction.
     * @return true if the direction was successfully changed, false otherwise
     */
    public boolean changeDirection(Direction direction){
        if(context.changeDirection(direction)) {
            pcs.firePropertyChange(PROP_GAME, null,null);
            return true;
        }
        return false;
    }

    /**
     * Pauses the game engine and change fsm state
     * @return true if the game was successfully paused, false otherwise
     */
    public boolean pause(){
        gameEngine.pause();
        return context.pause(gameEngine.getInterval());
    }

    /**
     * Resumes the GameEngine and change the fsm state,
     * @return return true if the game was successfully resumed, false otherwise
     */
    public boolean resume(){
        gameEngine.resume();
        return context.resume();
    }

    /**
     * Retrieves the maze representation
     * @return a bi dimensional array of characters representing the maze
     */
    public char[][] showMaze(){
        return context.showMaze();
    }

    /**
     * Retrieves the number of rows in the maze
     * @return the number of rows in the maze
     */
    public Integer getMazeRows(){
        return context.getMazeRows();
    }

    /**
     * Retrieves the number of columns in the maze
     * @return the number of columns in the maze
     */
    public Integer getMazeColumns(){
        return context.getMazeColumns();
    }

    /**
     * Retrieves the game information like score, lives and level
     * @return the string representation of the game information
     */
    public String showGameInfo(){
        return context.showGameInfo();
    }

    /**
     * Evolves the game based on the current Time, that currentTime is used to free the ghosts
     * Fire a gameProperty change Event if the game changes.
     * @param currentTime the current Time
     */
    public void evolve(long currentTime) {
        GameStatus gameStatus = context.evolve(currentTime);
        if(gameStatus == GameStatus.NEXT_LEVEL) {
            pcs.firePropertyChange(PROP_BOARD, null, null);
        }

        pcs.firePropertyChange(PROP_GAME, null, null);
    }

    /**
     * Adds a property change listener to the model Manager
     * @param listener the property changed listerner to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Adds a property changed listener to the specific property
     * @param property The property to listen the changes
     * @param listener The property change listener to be added
     */
    public void addPropertyChangeListener(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(property,listener);
    }

    /**
     * Retrieves the current program State
     * @return The current UiManager State
     */
    public UIManager getProgramState(){
        return UIManager;
    }

    /**
     * Changes the program state to the top5 Menu
     * Fires a menu property change event
     */
    public void changeToTop5(){
        this.UIManager = UIManager.TOP5;
        pcs.firePropertyChange(PROP_MENU,null,null);
    }

    /**
     * Initializes the game
     * Creates a new context and game Engine if null
     */
    public void initGame(){
        if(context == null)
            this.context = new Context();
        if(gameEngine == null) {
            this.gameEngine = new GameEngine();
            this.gameEngine.registerClient((gameEngine,currentTime) -> {evolve(currentTime);});
        }
    }

    /**
     * Changes the program state to the game
     * Starts the gameEngine, fires Menu and game property change Event
     */
    public void changeToGame(){
        gameEngine.start(GAME_ENGINE_TIME);
        this.UIManager = UIManager.GAME;
        pcs.firePropertyChange(PROP_MENU,null,null);
        pcs.firePropertyChange(PROP_GAME,null,null);
    }

    /**
     * Changes the program state to the main menu
     * Stops the game engine, clears the context, and fires menu and game property change events
     */
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

    /**
     * Retrieves the character at the specified position in the maze
     * @param row The row index of the position
     * @param column The column index of the position
     * @return The character at the specified position
     */
    public char receiveElement(int row, int column) {
        return context.receiveElement(row,column);
    }

    /**
     * Retrieves the current pacman direction
     * @return the current pacman direction
     */
    public Direction getDirection() {
        return context.getDirection();
    }

    /**
     * Checks if the given character represents a ghost
     * @param c the character to check
     * @return true if the caracter represents a ghost, false otherwise
     */
    public boolean charIsGhosts(char c) {
        return context.charIsGhost(c);
    }

    /**
     * Retrieves the current game Score
     * @return the current game score
     */
    public int getScore() {
        return context.getScore();
    }

    /**
     * Changes the program state to pause
     * Pauses the game and fire a game property change event
     */
    public void changeToPause() {
        pause();
        pcs.firePropertyChange(PROP_GAME,null,null);
    }

    /**
     * Changes the program state to resume
     * Resumes the game and fires a game property change event
     */
    public void changeToResume() {
        resume();
        pcs.firePropertyChange(PROP_GAME,null,null);
    }

    /**
     * Saves the game and fires the property change event
     */
    public void changeToSaveAndExit() {
        context.saveGame();
        pcs.firePropertyChange(PROP_GAME,null,null);
    }

    /**
     * Checks if exists any saved game
     * @return true if exists, false otherwise
     */
    public boolean checkIfSavedGamesExist() {
        return context.checkIfSavedGamesExist();
    }

    /**
     * Load a saved game
     */
    public void loadSavedGame() {
        context.loadSavedGame();
    }

    /**
     * Checks if the ghosts at the specific position is vulnerable
     * @param posX The X position of the ghost
     * @param posY The Y position of the ghost
     * @return true if the ghost is vulnerable, false otherwise
     */
    public boolean isVulnerableGhostPosition(int posX, int posY) {
        return context.isVulnerableGhostPosition(posX, posY);
    }

    /**
     * Checks if the ghosts at the specific position is dead
     * @param posX The X position of the ghost
     * @param posY The Y position of the ghost
     * @return true if the ghost is dead, false otherwise
     */
    public boolean isGhostDead(int posX, int posY) {
        return context.isGhostDead(posX, posY);
    }

    /**
     * Removes the saved game
     */
    public void removeSavedGame() {
        context.removeSavedGame();
    }

    /**
     * Checks if the current score is in the top5
     * @return true if the score is in the top5, false otherwise
     */
    public boolean isInTop5() {
        return context.isInTop5();
    }

    /**
     * Adds the current user score into the Top5
     * @param userName The username to be added
     */
    public void addIntoTop5(String userName) {
        context.addIntoTop5(userName);
    }

    /**
     * Retrives the Top5 scores
     * @return The list of top5 Usernames and respective scores
     */
    public List<Top5Data> getTop5() {
        return new Top5().getTop5();
    }
}