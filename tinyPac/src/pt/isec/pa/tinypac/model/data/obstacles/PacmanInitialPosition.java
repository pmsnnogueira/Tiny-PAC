package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.io.Serial;
import java.io.Serializable;

/**
 * The PacmanInitialPosition class represents the initial position of Pacman in a maze.
 * It implements the IMazeElement interface and is Serializable.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class PacmanInitialPosition implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    /**
     * Constructs a `PacmanInitialPosition` object with the default symbol.
     */
    public PacmanInitialPosition(){
        this.symbol = Obstacles.PACMAN_INITIAL_POSITION.getSymbol();
    }

    /**
     * Retrieves the symbol representing Pacman initial position.
     * @return The symbol representing Pacman initial position.
     */
    @Override
    public char getSymbol() {
        return symbol;
    }
}
