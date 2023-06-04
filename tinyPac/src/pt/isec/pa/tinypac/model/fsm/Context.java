package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.states.WaitForDirectionState;
import pt.isec.pa.tinypac.utils.Direction;

public class Context {
    private GameManager data;
    private IState state;
    private IState previousState;
    private long timeBeforePause;

    public Context(){
        this.data = new GameManager();
        this.previousState = null;
        this.timeBeforePause = 0;
        this.state = new WaitForDirectionState(this,data);
    }

    public State getState(){
        if(state == null)
            return null;
        return state.getState();
    }

    public State getPreviousState(){
        if(previousState == null)
            return null;
        return previousState.getState();
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


    public Integer getMazecolumns(){
        return data.getMazeColumns();
    }

    public void evolve(long currentTime) {
        state.evolve(currentTime);
    }

    public boolean pause(long currentTime){
        this.timeBeforePause = currentTime;
        this.previousState = state;
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
}
