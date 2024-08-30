package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Power class represents a power-up in a maze.
 * It implements the IMazeElement interface and is Serializable.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Power implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;
    public static final int POWER_POINTS = 10;

    /**
     * Constructs a Power object.
     * Sets the symbol to the character representing the power-up.
     */
    public Power(){
        this.symbol = Obstacles.POWER.getSymbol();
    }

    /**
     * Retrieves the symbol representing the power-up.
     * @return The symbol representing the power-up.
     */
    @Override
    public char getSymbol() {
        return symbol;
    }
}
