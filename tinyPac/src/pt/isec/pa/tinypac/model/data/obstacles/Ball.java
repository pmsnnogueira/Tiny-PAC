package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Ball class represents a ball element in a maze.
 * It implements the IMazeElement interface and is Serializable.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Ball implements IMazeElement, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    public static final int BALL_POINTS = 1;

    /**
     * Constructs a Ball object with the default symbol.
     */
    public Ball(){
        this.symbol = Obstacles.BALL.getSymbol();
    }

    /**
     * Retrieves the symbol representing the ball element.
     * @return The symbol representing the ball.
     */
    @Override
    public char getSymbol() {
        return symbol;
    }
}
