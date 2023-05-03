package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

public class GameState extends StateAdapter {

    public GameState(Context context, GameManager data){
        super(context, data);
    }

    @Override
    public State getState() {
        return State.GAME;
    }

    @Override
    public boolean changeDirection(Direction direction) {
        return data.changeDirection(direction);
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        data.evolve(gameEngine,currentTime);

        if(!data.controlGame()){
            changeState(State.WAIT_FOR_DIRECTIONS);
        }
    }

    @Override
    public boolean pause() {
        changeState(State.PAUSE);
        return true;
    }
}
