package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

public class GhostVulnerableState extends StateAdapter {

    public GhostVulnerableState(Context context, GameManager data){
        super(context, data);
    }

    @Override
    public State getState() {
        return State.GHOST_VULNERABLE;
    }

    @Override
    public boolean changeDirection(Direction direction) {
        return data.changeDirection(direction);
    }
    @Override
    public void evolve(long currentTime) {

        data.evolve(currentTime);

        //int result = data.controlGame();

    }

    @Override
    public boolean pause() {
        changeState(State.PAUSE);
        return true;
    }
}