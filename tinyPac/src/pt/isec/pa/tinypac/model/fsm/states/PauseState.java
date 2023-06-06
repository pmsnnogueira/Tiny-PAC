package pt.isec.pa.tinypac.model.fsm.states;

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
        switch (context.getPreviousState()){
            case WAIT_FOR_DIRECTIONS -> changeState(State.WAIT_FOR_DIRECTIONS);
            case LOCKED_GHOSTS -> changeState(State.LOCKED_GHOSTS);
            case GAME -> changeState(State.GAME);
            case GHOST_VULNERABLE -> changeState(State.GHOST_VULNERABLE);
        }

        return true;
    }
}
