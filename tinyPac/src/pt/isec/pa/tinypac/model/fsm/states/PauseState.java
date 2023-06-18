package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

/**
 * The pause State class represents the state where the game is paused
 * It extends the State Adapter
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class PauseState extends StateAdapter {

    /**
     * Constructs a PauseState object with the specified context and game manager.
     * @param context The game context.
     * @param data The game manager.
     */
    public PauseState(Context context, GameManager data){
        super(context, data);
    }

    /**
     * Retrieves the state of this state object.
     * @return The state PAUSE.
     */
    @Override
    public State getState() {
        return State.PAUSE;
    }

    /**
     * Resumes the game by transitioning to the previous state before pausing.
     * The transition depends on the previous state:
     * If the previous state was WAIT_FOR_DIRECTIONS, transitions to WAIT_FOR_DIRECTIONS state.
     * If the previous state was LOCKED_GHOSTS, transitions to LOCKED_GHOSTS state.
     * If the previous state was GAME, transitions to GAME state.
     * If the previous state was GHOST_VULNERABLE, transitions to GHOST_VULNERABLE state.
     * @return true if the game was successfully resumed, false otherwise.
     */
    @Override
    public boolean resume() {
        switch (context.getPreviousState()){
            case WAIT_FOR_DIRECTIONS -> changeState(State.WAIT_FOR_DIRECTIONS);
            case LOCKED_GHOSTS -> changeState(State.LOCKED_GHOSTS);
            case GAME -> changeState(State.GAME);
            case GHOST_VULNERABLE -> changeState(State.GHOST_VULNERABLE);
        }

        return true;
    }
}
