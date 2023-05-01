package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.IState;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.states.WaitForDirectionState;
import utils.Direction;


public class Context {
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
        if(data.changeDirection(direction))
            return true;
        return false;
    }

    public char[][] showMaze(){
        return data.showMaze();
    }

    public GameManager getGameManager() {
        return data;
    }
}
