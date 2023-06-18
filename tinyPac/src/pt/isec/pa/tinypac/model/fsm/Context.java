package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.states.WaitForDirectionState;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.*;


/**
 * The Context class manages the game Context and state transitions
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Context {
    private GameManager data;
    private IState state;
    private State previousState;

    private final static String SAVE_PATH = "files/saves/";
    private final static String SAVE_NAME = "tiny_Pac01.json";

    /**
     * Constructs a Context object and initializes the gameManager and initial fsm state
     */
    public Context(){
        this.data = new GameManager();
        this.state = new WaitForDirectionState(this,data);
        this.previousState = getState();
    }

    /**
     * Retrieves the current fsm state
     * @return The current fsm State
     */
    public State getState(){
        if(state == null)
            return null;
        return state.getState();
    }

    /**
     * Retrieves the previous fsm state
     * @return The previous fsm State
     */
    public State getPreviousState(){
        return previousState;
    }

    /**
     * Changes the state of the fsm to the newState
     * @param newState The new state to change to
     */
    //Nao mudar este changeState para public nem protected
    void changeState(IState newState){
        this.state = newState;
    }

    /**
     * Changes the pacman direction
     * @param direction The new Direction to change to
     * @return true if the direction was successfully changed, false otherwise
     */
    public boolean changeDirection(Direction direction){
        return state.changeDirection(direction);
    }

    /**
     * Retrieves the maze to be shown
     * @return The maze as a bi dimensional array of characters
     */
    public char[][] showMaze(){
        return data.showMaze();
    }

    /**
     * Retrieves the game information
     * @return The game information as a String
     */
    public String showGameInfo(){
        return data.showGameInfo();
    }

    /**
     * Retrieves the number of rows in the maze.
     * @return The number of rows.
     */
    public Integer getMazeRows(){
        return data.getMazeRows();
    }

    /**
     * Retrieves the number of columns in the maze.
     * @return The number of columns.
     */
    public Integer getMazeColumns(){
        return data.getMazeColumns();
    }

    /**
     * Evolves the game
     * @param currentTime The current Time, is used to free the ghosts
     * @return true if the game was successfully evolved, false otherwise
     */
    public boolean evolve(long currentTime) {
        return state.evolve(currentTime);
    }

    /**
     * Pauses the game
     * @return True if the game was successfully paused, false otherwise
     */
    public boolean pause(){
        if(getState() != State.PAUSE)
            this.previousState = getState();
        return state.pause();
    }

    /**
     * Resumes the game
     * @return True if the game was successfully resumed, false otherwise
     */
    public boolean resume(){
        return state.resume();
    }

    /**
     * Returns the element at the specific position in the maze
     * @param row The Y Position
     * @param column The X Position
     * @return The character element at the specific position.
     */
    public char receiveElement(int row, int column) {
        return data.receiveElement(row,column);
    }

    /**
     * Returns the pacman direction
     * @return Direction The pacman direction
     */
    public Direction getDirection() {
        return data.getDirection();
    }

    /**
     * Checks if the given character represents a ghosts
     * @param c The caracter to check
     * @return true if the character represents a ghosts, false otherwise
     */
    public boolean charIsGhost(char c) {
        return data.charIsGhosts(c);
    }

    /**
     * Retrieves the current score of the game
     * @return The current score
     */
    public int getScore() {
        return data.getScore();
    }


    /**
     * Checks if a saved game exists
     * @return true if a saved game exists, false otherwise
     */
    public boolean checkIfSavedGamesExist() {
        return data.checkIfSavedGamesExist();
    }

    /**
     * Checks if the given position is a ghost vulnerable position
     * @param posX The x position
     * @param posY The y position
     * @return true if the position is a vulnerable ghost position, false otherwise
     */
    public boolean isVulnerableGhostPosition(int posX, int posY) {
        return data.isVulnerableGhostPosition(posX, posY);
    }

    /**
     * Checks if the ghost at the given position is dead
     * @param posX the x Position
     * @param posY the y Position
     * @return true if the ghost at the position is dead, false otherwise
     */
    public boolean isGhostDead(int posX, int posY) {
        return data.isGhostDead(posX,posY);
    }

    /**
     * Removes the saved game
     */
    public void removeSavedGame() {
        data.removeSavedGame();
    }

    /**
     * Checks if the current score is in the Top5
     * @return true if the game is in the top5, false otherwise
     */
    public boolean isInTop5() {
        return data.isInTop5();
    }

    /**
     *  Adds the user game into the top5
     * @param userName The username to be added
     */
    public void addIntoTop5(String userName) {
        data.addIntoTop5(userName);
    }

    /**
     * Saves the current game
     */
    public void saveGame(){

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_PATH + SAVE_NAME))
        ){
            oos.writeObject(data.getGame());
            oos.writeObject(previousState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createGameManager(Game game, State state){
        this.data = new GameManager(game);
        this.state = state.createState(this,data);
        this.previousState = getState();
    }

    /**
     * Loads the saved Game
     */
    public void loadSavedGame() {

        Game gameAux = null;
        try (ObjectInputStream oos = new ObjectInputStream(
                new FileInputStream(SAVE_PATH + SAVE_NAME))
        ){

            gameAux = (Game) oos.readObject();
            previousState = (State) oos.readObject();

            createGameManager(gameAux,previousState);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
