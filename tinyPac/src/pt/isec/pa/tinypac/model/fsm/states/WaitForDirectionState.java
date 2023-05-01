package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import utils.Direction;

public class WaitForDirectionState extends StateAdapter {

    public WaitForDirectionState(Context context, GameManager data){
        super(context, data);
        //loadGame();
    }



    @Override
    public State getState() {
        return State.WAIT_FOR_DIRECTIONS;
    }

    @Override
    public boolean changeDirection(Direction direction) {
        context.changeDirection(direction);
        changeState(State.GAME);
        return true;
    }

    @Override
    public boolean evolve(IGameEngine gameEngine, long currentTime) {
        return false;
    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public boolean resume() {
        return false;
    }



}
