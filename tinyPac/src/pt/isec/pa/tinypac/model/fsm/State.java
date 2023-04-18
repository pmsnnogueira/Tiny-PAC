package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.fsm.states.*;

public enum State {

    WAIT_TO_MOVE_PACMAN,
    GAME,
    VULNERABLE_GHOSTS,
    LEVEL_COMPLETE,
    PAUSE,
    GAME_OVER;

    IState createState(Context context, Game data){
        return switch (this){
            case WAIT_TO_MOVE_PACMAN -> new WaitToMovePacmanState(context, data);
            case GAME -> new GameState(context, data);
            case VULNERABLE_GHOSTS -> new VulnerableGhostState(context, data);
            case LEVEL_COMPLETE -> new LevelCompleteState(context, data);
            case PAUSE -> new Pause(context, data);
            case GAME_OVER -> new GameOverState(context, data);
        };
    }
}
