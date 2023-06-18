package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Empty class represents an empty space element in a maze.
 * It implements the IMazeElement interface and is Serializable.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Empty implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    /**
     * Constructs an `Empty` object with the default symbol.
     */
    public Empty(){
        this.symbol = ' ';
    }

    /**
     * Retrieves the symbol representing the empty space element.
     * @return The symbol representing the empty space.
     */
    @Override
    public char getSymbol() {
        return symbol;
    }
}
