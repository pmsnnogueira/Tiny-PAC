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
        return super.resume();
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
