package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.GameStatus;

/**
 * The WaitForDirectonState class represents the state
 * where the game is waiting for the player to input the pacman direction
 * It extends the State Adapter
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class WaitForDirectionState extends StateAdapter {

    /**
     * Constructs a WaitForDirectionState object with the specified context and gameManager
     * @param context The game Context
     * @param data The game Manager
     */
    public WaitForDirectionState(Context context, GameManager data){
        super(context, data);
        if(!data.loadMapLevel()){
            System.out.println("\nCould not load levels\nClosing Game");
            System.exit(-1);
        }
    }

    /**
     * Retrieves the state of this state Object
     * @return The state WAIT_FOR_DIRECTIONS
     */
    @Override
    public State getState() {
        return State.WAIT_FOR_DIRECTIONS;
    }

    /**
     * Changes the direction of the pacman based on the players input direction
     * Transition to the LOCKED_GHOST state
     * @param direction The new direction to change To
     * @return always true
     */
    @Override
    public boolean changeDirection(Direction direction) {
        data.changeDirection(direction);
        changeState(State.LOCKED_GHOSTS);
        return true;
    }

    /**
     * Evolves the game
     * @param currentTime the current Time, is used to free the ghosts
     * @return
     */
    @Override
    public GameStatus evolve(long currentTime) {
        return GameStatus.WAIT_FOR_KEY_DIRECTION;
    }

    /**
     * Pauses the game by transtioning to the pause state
     * @return always true
     */
    @Override
    public boolean pause() {
        changeState(State.PAUSE);
        return true;
    }

}