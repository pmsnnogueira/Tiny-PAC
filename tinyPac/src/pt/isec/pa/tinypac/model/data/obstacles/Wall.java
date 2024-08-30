package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.io.Serial;
import java.io.Serializable;

/**
 * The `Wall` class represents a wall element in a maze.
 * It implements the `IMazeElement` interface and is `Serializable`.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Wall implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    /**
     * Constructs a Wall object.
     * Sets the symbol to the character representing the wall.
     */
    public Wall(){
        this.symbol = Obstacles.WALL.getSymbol();
    }

    /**
     * Retrieves the symbol representing the wall.
     * @return The symbol representing the wall.
     */
    @Override
    public char getSymbol() {
        return symbol;
    }
}
