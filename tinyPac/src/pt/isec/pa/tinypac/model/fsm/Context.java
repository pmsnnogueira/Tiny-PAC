package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.states.WaitToMovePacmanState;


public class Context {
    private IState state;
    private GameManager data;

    public Context(){
        this.data = new GameManager();
        state = new WaitToMovePacmanState(this, data);
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

    public char[][] showMaze(){
        return state.showMaze();
    }

    public GameManager getData() {
        return data;
    }
}
