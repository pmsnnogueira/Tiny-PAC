package pt.isec.pa.tinypac.model.data;

import java.io.Serializable;

/**
 * The GameObjects class is an abstract class that represents objects in the game.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public abstract class GameObjects implements IMazeElement , Serializable {
    protected Game game;

    /**
     * Constructs a GameObjects object.
     * @param game The game instance.
     */
    protected GameObjects(Game game){
        this.game = game;
    }

    /**
     * Allows the object to evolve or perform actions in the game.
     * @return True if the object evolves successfully, false otherwise.
     */
    abstract public boolean evolve();

    /**
     * Returns the object to its base or initial state.
     */
    abstract public void returnToBase();
}
