package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.GameStatus;


/**
 * The GameState class represents the state where the game is actively being played.
 * It extends the StateAdapter class.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class GameState extends StateAdapter {

    /**
     * Constructs a GameState object with the specified context and game manager.
     * @param context The game context.
     * @param data The game manager.
     */
    public GameState(Context context, GameManager data){
        super(context, data);
    }

    /**
     * Retrieves the state of this state object.
     * @return The state GAME.
     */
    @Override
    public State getState() {
        return State.GAME;
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
     * Evolves the game state based on the current time.
     * Checks for different game conditions such as Pacman's death, end of level, or vulnerability of ghosts.
     * @param currentTime The current time.
     * @return true if the game state was successfully evolved, false otherwise.
     */
    @Override
    public GameStatus evolve(long currentTime) {
        GameStatus gameStatus;
        gameStatus = data.evolve(currentTime);

        int result = data.controlGameState();
        if(result == -1){
            //Restart the level or GameOver
            //System.out.println("Pacman died");
            int resPac = data.pacmanManager();
            if(resPac == 1){
                //Pacman has lives and can continue game
                changeState(State.WAIT_FOR_DIRECTIONS);
            }if(resPac == -1){
                changeState(State.GameOver);
            }
        }else if(result == 1){
            data.ghostsVulnerable(true);
            changeState(State.GHOST_VULNERABLE);
        }else if(result == 2){
            //EndLevel
            System.out.println("End Level");
            gameStatus = GameStatus.NEXT_LEVEL;
            changeState(State.WAIT_FOR_DIRECTIONS);
        }

        return gameStatus;
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
