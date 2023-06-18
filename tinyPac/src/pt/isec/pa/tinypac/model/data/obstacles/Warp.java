package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Warp class represents a warp element in a maze.
 * It implements the IMazeElement interface and is Serializable.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Warp implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    private Position position;

    /**
     * Constructs a `Warp` object with the specified position.
     * Sets the symbol to the character representing the warp.
     *
     * @param posX The X-coordinate of the warp position.
     * @param posY The Y-coordinate of the warp position.
     */
    public Warp(Integer posX, Integer posY){
        this.symbol = Obstacles.WARP.getSymbol();
        this.position = new Position(posX, posY);
    }

    /**
     * Retrieves the symbol representing the warp.
     * @return The symbol representing the warp.
     */
    @Override
    public char getSymbol() {
        return symbol;
    }

    /**
     * Retrieves the X-coordinate of the warp position.
     * @return The X-coordinate of the warp position.
     */
    public Integer getPositionX() {
        return position.getPosX();
    }

    /**
     * Retrieves the Y-coordinate of the warp position.
     * @return The Y-coordinate of the warp position.
     */
    public Integer getPositionY() {
        return position.getPosY();
    }

    /**
     * Retrieves the position of the warp as a new `Position` object.
     * @return The position of the warp.
     */
    public Position getPosition() {
        return new Position(position);
    }
}
