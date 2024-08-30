package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Fruit class represents a fruit element in a maze.
 * It implements the IMazeElement interface and is Serializable.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Fruit implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    public static final int FRUIT_POINTS = 25;

    /**
     * Constructs a Fruit object with the default symbol.
     */
    public Fruit(){
        this.symbol = Obstacles.FRUIT.getSymbol();
    }

    /**
     * Retrieves the symbol representing the fruit element.
     * @return The symbol representing the fruit.
     */
    @Override
    public char getSymbol() {
        return symbol;
    }
}
