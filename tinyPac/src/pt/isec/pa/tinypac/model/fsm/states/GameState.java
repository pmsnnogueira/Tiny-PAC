package pt.isec.pa.tinypac.model.fsm.states;

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
    public void evolve(long currentTime) {
        data.evolve(currentTime);

        int result = data.controlGame();
        if(result == -1){
            //Restart the level or GameOver
            //System.out.println("Pacman died");
            int resPac = data.pacmanManager();
            if(resPac == 1){
                //Pacman has lives and can continue game
                changeState(State.WAIT_FOR_DIRECTIONS);
                return;
            }if(resPac == -1){
                changeState(State.GameOver);
                return;
            }
        }else if(result == 1){
            //System.out.println("Changing to GhostVulnerableState");
            data.ghostsVulnerable();
            changeState(State.GHOST_VULNERABLE);
            return;
        }else if(result == 2){
            //EndLevel
            System.out.println("End Level");
            changeState(State.WAIT_FOR_DIRECTIONS);
            return;
        }
    }

    @Override
    public boolean pause() {
        changeState(State.PAUSE);
        return true;
    }
}
