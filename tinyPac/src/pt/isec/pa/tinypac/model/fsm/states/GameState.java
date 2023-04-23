package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;

public class GameState extends StateAdapter {

    public GameState(Context context, GameManager data){
        super(context, data);
    }

    @Override
    public State getState() {
        return State.GAME;
    }




}
