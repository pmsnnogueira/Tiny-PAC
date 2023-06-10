package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

public class GhostVulnerableState extends StateAdapter {

    private static final long LOCKTIME = 10;         //Seconds
    private long initialTime;
    private long maxTime;

    public GhostVulnerableState(Context context, GameManager data){
        super(context, data);
        this.initialTime = 0;
        this.maxTime = 0;
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
    public boolean evolve(long currentTime) {
        boolean needUpdate = data.evolve(currentTime);
        int res;

        res = data.controlGameVulnerableState();
        if(res == 1) {
            changeState(State.GAME);
        }if(res == -1) {
            System.out.println("Retorno -1");
            /*int resPac = data.pacmanManager();
            if(resPac == -1)
                changeState(State.GameOver);
            else if(resPac == 1)
                changeState(State.WAIT_FOR_DIRECTIONS);*/
        }

        if(unlockGhosts(currentTime)){
        }

        return needUpdate;
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
            data.ghostsVulnerable(false);
            data.pacmanPower(false);
            changeState(State.GAME);
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