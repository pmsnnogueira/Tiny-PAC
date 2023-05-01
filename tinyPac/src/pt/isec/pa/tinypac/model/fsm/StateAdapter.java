package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;
import utils.Direction;

public abstract class StateAdapter implements IState {

    protected Context context;
    protected GameManager data;

    protected StateAdapter(Context context, GameManager data){
        this.context = context;
        this.data = data;
    }

    protected void changeState(State newState){
        context.changeState(newState.createState(context,data));
    }

    @Override
    public boolean changeDirection(Direction direction) {
        return false;
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        return;
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
