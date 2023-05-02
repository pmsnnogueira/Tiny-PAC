package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.states.WaitForDirectionState;
import pt.isec.pa.tinypac.utils.Direction;


public class Context implements IGameEngineEvolve {
    private IState state;
    private GameManager data;

    public Context(GameManager data){
        this.data = data;
        state = new WaitForDirectionState(this, data);
    }

    public State getState(){
        if(state == null)
            return null;
        return state.getState();
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

    public GameManager getGameManager() {
        return data;
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        state.evolve(gameEngine,currentTime);
    }

    public boolean pause(){
        return state.pause();
    }
}
