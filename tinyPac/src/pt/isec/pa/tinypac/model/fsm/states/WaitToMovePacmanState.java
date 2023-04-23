package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;

import java.util.ArrayList;

public class WaitToMovePacmanState extends StateAdapter {

    public WaitToMovePacmanState(Context context, GameManager data){
        super(context, data);
    }

    @Override
    public State getState() {
        return State.WAIT_TO_MOVE_PACMAN;
    }

    @Override
    public boolean startGame() {
        if(!data.generateMapLevel())
            return false;

        //data.makeGhostMovements();


        //changeState(State.GAME);
        return true;
    }

    @Override
    public Maze maze() {
        return data.getMaze();
    }
}
