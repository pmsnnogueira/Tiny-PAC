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

        State previousState = switch (context.getState()){
            case WAIT_FOR_DIRECTIONS -> new WaitForDirectionState(context , data);
            case LOCKED_GHOSTS -> new LockedGhostsState(context , data);
            case GAME -> new GameState(context , data);
            case GHOST_VULNERABLE -> new GhostVulnerableState(context,data);
            
        };
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
