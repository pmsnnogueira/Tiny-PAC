package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.states.WaitForDirectionState;
import pt.isec.pa.tinypac.utils.Direction;

public class Context {
    private GameManager data;
    private IState state;
    private State previousState;
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

    public void saveGame() {
        data.saveGame();
    }

    public void loadSavedGame() {
        data.loadSavedGame();
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
}
