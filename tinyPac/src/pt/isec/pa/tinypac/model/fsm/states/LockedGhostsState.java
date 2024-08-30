package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.GameStatus;


/**
 * The LockedGhostState class represents the state where the ghosts are locked
 * It extends the stateAdapter class
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class LockedGhostsState extends StateAdapter {

    private static final long LOCKTIME = 1;         //Seconds
    private long initialTime;
    private long maxTime;


    /**
     * Constructs a LockedGhostsState object with the specified context and game manager.
     * @param context The game context.
     * @param data The game manager.
     */
    public LockedGhostsState(Context context, GameManager data){
        super(context, data);
        this.initialTime = 0;
        this.maxTime = 0;
    }

    /**
     * Retrieves the state of this state object.
     * @return The state LOCKED_GHOSTS.
     */
    @Override
    public State getState() {
        return State.LOCKED_GHOSTS;
    }

    /**
     * Changes the direction of the pacman based on the player's input direction.
     * @param direction The new direction to change to.
     * @return true if the direction was successfully changed, false otherwise.
     */
    @Override
    public boolean changeDirection(Direction direction) {
        return data.changeDirection(direction);
    }

    /**
     * Evolves the game state based.
     * If the ghosts are unlocked, transitions to the GAME state.
     * @param currentTime The current time.
     * @return true if the game state was successfully evolved, false otherwise.
     */
    @Override
    public GameStatus evolve(long currentTime) {
        if(unlockGhosts(currentTime))
            changeState(State.GAME);

        return data.evolve(currentTime);
    }

    private long convertSecondsToNano(long seconds){
        return seconds * 1000000000;
    }

    /**
     * Unlocks the ghosts if the specified time has passed.
     * @param currentTime The current time.
     * @return true if the ghosts were unlocked, false otherwise.
     */
    private boolean unlockGhosts(long currentTime){
        if(initialTime == 0){
            initialTime = currentTime;
            maxTime = initialTime + convertSecondsToNano(LOCKTIME);
            return false;
        }

        if(currentTime >= maxTime) {
            data.unlockGosts(false);
            return true;
        }
        return false;
    }


    /**
     * Pauses the game by transitioning to the PAUSE state.
     * @return true if the game was successfully paused, false otherwise.
     */
    @Override
    public boolean pause() {
        changeState(State.PAUSE);
        return true;
    }
}
