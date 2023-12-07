package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.GameStatus;

/**
 * The IState interface defines the method managing the game State
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public interface IState {

    /**
     * Retrieves the current State
     * @return The current State
     */
    State getState();

    /**
     * Changes the pacman direction
     * @param direction The new direction to change To
     * @return true if the direction was successfully changed, false otherwise
     */
    boolean changeDirection(Direction direction);

    /**
     * Evolves the game
     * @param currentTime the current Time, is used to free the ghosts
     * @return if the game was evolved, false otherwise
     */
    GameStatus evolve(long currentTime);

    /**
     * Pauses the game
     * @return true if the game was successfully pauses, false otherwise
     */
    boolean pause();

    /**
     * Resumes the game
     * @return true if the game was successfully resumed, false otherwise
     */
    boolean resume();
}
