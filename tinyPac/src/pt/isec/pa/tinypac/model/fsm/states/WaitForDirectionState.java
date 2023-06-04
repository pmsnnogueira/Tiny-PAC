package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

public class WaitForDirectionState extends StateAdapter {

    public WaitForDirectionState(Context context, GameManager data){
        super(context, data);
        if(!data.loadMapLevel()){
            System.out.println("\nCould not load levels\nClosing Game");
            System.exit(-1);
        }
    }

    @Override
    public State getState() {
        return State.WAIT_FOR_DIRECTIONS;
    }

    @Override
    public boolean changeDirection(Direction direction) {
        data.changeDirection(direction);
        changeState(State.GameOver);
        return true;
    }

    @Override
    public void evolve(long currentTime) {
        data.evolve(currentTime);
    }

    @Override
    public boolean pause() {
        changeState(State.PAUSE);
        return true;
    }


}
