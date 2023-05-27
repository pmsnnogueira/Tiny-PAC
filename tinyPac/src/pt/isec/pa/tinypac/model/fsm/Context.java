package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.states.WaitForDirectionState;
import pt.isec.pa.tinypac.utils.Direction;


public class Context {
    private GameManager data;
    private IState state;
    private IState previousState;

    private long timeBeforePause;

    public Context(GameManager data){
        this.data = data;
        state = new WaitForDirectionState(this, data);
        this.previousState = null;
        this.timeBeforePause = 0;
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
}
