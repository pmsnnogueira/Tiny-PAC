package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;

public class LockedGhostsState extends StateAdapter {

    public LockedGhostsState(Context context, GameManager data){
        super(context, data);
    }

    @Override
    public State getState() {
        return State.LOCKED_GHOSTS;
    }

    @Override
    public boolean evolve(IGameEngine gameEngine, long currentTime) {
        return evolve(gameEngine, currentTime);
    }

    @Override
    public boolean pause() {
        changeState(State.PAUSE);
        return true;
    }
}
