package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.states.*;

public enum State {

    WAIT_FOR_DIRECTIONS,
    GAME,
    LOCKED_GHOSTS,
    PAUSE,
    GameOver;

    IState createState(Context context, GameManager data){
        return switch (this){
            case WAIT_FOR_DIRECTIONS -> new WaitForDirectionState(context, data);
            case GAME -> new GameState(context, data);
            case LOCKED_GHOSTS -> new LockedGhostsState(context, data);
            case PAUSE -> new PauseState(context, data);
            case GameOver -> new GameOverState(context,data);
        };
    }
}
