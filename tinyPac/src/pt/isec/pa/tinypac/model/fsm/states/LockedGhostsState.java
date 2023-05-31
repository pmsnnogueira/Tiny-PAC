package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

public class LockedGhostsState extends StateAdapter {

    private static final long LOCKTIME = 1;         //Seconds
    private long initialTime;
    private long maxTime;


    public LockedGhostsState(Context context, GameManager data){
        super(context, data);
        this.initialTime = 0;
        this.maxTime = 0;
    }

    @Override
    public State getState() {
        return State.LOCKED_GHOSTS;
    }

    @Override
    public boolean changeDirection(Direction direction) {
        return data.changeDirection(direction);
    }

    public void evolve(long currentTime) {
        if(unlockGhosts(currentTime))
            changeState(State.GAME);

        data.evolve(currentTime);
    }

    private long convertSecondsToNano(long seconds){
        return seconds * 1000000000;
    }

    private boolean unlockGhosts(long currentTime){
        if(initialTime == 0){
            initialTime = currentTime;
            maxTime = initialTime + convertSecondsToNano(LOCKTIME);
            return false;
        }

        if(currentTime >= maxTime) {
            System.out.println("Unlocking Ghosts");
            data.unlockGosts(false);
            return true;
        }
        return false;
    }


    @Override
    public boolean pause() {
        changeState(State.PAUSE);
        return true;
    }
}
