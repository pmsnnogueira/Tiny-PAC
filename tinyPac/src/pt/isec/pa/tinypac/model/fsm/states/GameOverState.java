package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.data.Top5;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.ui.gui.resources.SoundManager;

/**
 * The GameOverState class represents the state when the game is over.
 * It extends the StateAdapter class.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class GameOverState extends StateAdapter {

    /**
     * Constructs a GameOverState object with the specified context and game manager.
     * @param context The game context.
     * @param data The game manager.
     */
    public GameOverState(Context context, GameManager data){
        super(context, data);
        SoundManager.play("pacman_death.mp3");
    }

    /**
     * Retrieves the state of this state object.
     * @return The state GameOver.
     */
    @Override
    public State getState() {
        return State.GameOver;
    }
}
