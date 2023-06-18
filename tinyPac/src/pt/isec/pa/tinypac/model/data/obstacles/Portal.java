package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Portal class represents a portal in a maze that connects two positions.
 * It implements the IMazeElement interface and is Serializable.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Portal implements IMazeElement , Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final char symbol;

    private final int posX;
    private final int posY;

    /**
     * Constructs a Portal object with the specified position coordinates.
     * @param posX The X-coordinate of the portal's position.
     * @param posY The Y-coordinate of the portal's position.
     */
    public Portal(int posX, int posY){
        this.symbol = Obstacles.PORTAL.getSymbol();
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Retrieves the X-coordinate of the portal's position.
     * @return The X-coordinate of the portal's position.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Retrieves the Y-coordinate of the portal's position.
     * @return The Y-coordinate of the portal's position.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Retrieves the symbol representing the portal.
     * @return The symbol representing the portal.
     */
    @Override
    public char getSymbol() {
        return symbol;
    }
}
