package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.states.WaitForDirectionState;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.*;


public class Context {
    private GameManager data;
    private IState state;
    private State previousState;

    private final static String SAVE_PATH = "files/saves/";
    private final static String SAVE_NAME = "tiny_Pac01.json";
    public Context(){
        this.data = new GameManager();
        this.state = new WaitForDirectionState(this,data);
        this.previousState = getState();
    }

    public State getState(){
        if(state == null)
            return null;
        return state.getState();
    }

    public State getPreviousState(){
        return previousState;
    }

    //Nao mudar este changeState para public nem protected
    void changeState(IState newState){
        this.state = newState;
    }

    public boolean changeDirection(Direction direction){
        return state.changeDirection(direction);
    }

    public char[][] showMaze(){
        return data.showMaze();
    }

    public String showGameInfo(){
        return data.showGameInfo();
    }

    public Integer getMazeRows(){
        return data.getMazeRows();
    }
    public Integer getMazeColumns(){
        return data.getMazeColumns();
    }

    public boolean evolve(long currentTime) {
        return state.evolve(currentTime);
    }

    public boolean pause(long currentTime){
        if(getState() != State.PAUSE)
            this.previousState = getState();
        return state.pause();
    }

    public boolean resume(){
        return state.resume();
    }

    public char receiveElement(int row, int column) {
        return data.receiveElement(row,column);
    }

    public Direction getDirection() {
        return data.getDirection();
    }

    public boolean charIsGhost(char c) {
        return data.charIsGhosts(c);
    }

    public int getScore() {
        return data.getScore();
    }


    public boolean checkIfSavedGamesExist() {
        return data.checkIfSavedGamesExist();
    }


    public boolean isVulnerableGhostPosition(int posX, int posY) {
        return data.isVulnerableGhostPosition(posX, posY);
    }

    public boolean isGhostDead(int posX, int posY) {
        return data.isGhostDead(posX,posY);
    }

    public void removeSavedGame() {
        data.removeSavedGame();
    }

    public boolean isInTop5() {
        return data.isInTop5();
    }

    public void addIntoTop5(String userName) {
        data.addIntoTop5(userName);
    }

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
