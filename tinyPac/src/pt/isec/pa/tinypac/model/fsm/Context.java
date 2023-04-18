package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fsm.states.WaitToMovePacmanState;


public class Context {
    private IState state;
    private Game data;

    public Context(){
        //Read the first level
        data = new Game();
        state = new WaitToMovePacmanState(this, data);
    }

    public State getState(){
        return state.getState();
    }

    //Nao mudar este changeState para public nem protected
    void changeState(IState newState){
        this.state = newState;
    }



}
