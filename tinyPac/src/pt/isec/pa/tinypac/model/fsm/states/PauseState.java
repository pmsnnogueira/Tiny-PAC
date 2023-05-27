package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

public class PauseState extends StateAdapter {

    public PauseState(Context context, GameManager data){
        super(context, data);
    }

    @Override
    public State getState() {
        return State.PAUSE;
    }

    @Override
    public boolean resume() {

        State state = context.getState();

        if(state == State.WAIT_FOR_DIRECTIONS){
            changeState(State.WAIT_FOR_DIRECTIONS);
            return true;
        }
        if(state == State.LOCKED_GHOSTS){
            changeState(State.LOCKED_GHOSTS);
            return true;
        }
        if(state == State.GAME){
            changeState(State.GAME);
            return true;
        }
        if(state == State.GHOST_VULNERABLE){
            changeState(State.GHOST_VULNERABLE);
            return true;
        }

        return false;
    }

    @Override
    public void evolve(long currentTime) {
        return;
    }

    @Override
    public boolean changeDirection(Direction direction) {
        return false;
    }
}
