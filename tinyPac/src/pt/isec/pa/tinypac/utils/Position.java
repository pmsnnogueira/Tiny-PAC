package pt.isec.pa.tinypac.utils;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a position with x and y coordinates.
 * Positions are serializable.
 */
public class Position implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int posX;
    private int posY;

    /**
     * Constructs a position with the given x and y coordinates.
     * @param posX The x coordinate of the position.
     * @param posY The y coordinate of the position.
     */
    public Position(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Constructs a position with the same coordinates as the given position.
     * @param position The position to copy the coordinates from.
     */
    public Position(Position position){
        this.posX = position.getPosX();
        this.posY = position.getPosY();
    }

    /**
     * Sets the x and y coordinates of the position.
     * @param posX The new x coordinate.
     * @param posY The new y coordinate.
     */
    public void setPos(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Gets the x coordinate of the position.
     * @return The x coordinate.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Gets the y coordinate of the position.
     * @return The y coordinate.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Sets the y coordinate of the position.
     * @param posY The new y coordinate.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Sets the x coordinate of the position.
     * @param posX The new x coordinate.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Compares this position to the specified object.
     * The result is {@code true} if and only if the argument is not null
     * and is a Position object that has the same coordinates as this position.
     * @param obj The object to compare this position against.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this)
            return true;

        if (obj == null)
            return false;

        /*if (this.getClass() == obj.getClass()) {
            return (((Position) obj).getPosX() == this.getPosX() && ((Position) obj).getPosY() == this.getPosY());
        }*/

        if(!(obj instanceof Position aux))
            return false;

        return (aux.getPosX() == this.getPosX() && aux.getPosY() == this.getPosY());
    }

    /**
     * Computes a hash code for this position.
     * @return The hash code value for this position.
     */
    @Override
    public int hashCode() {
        return getPosY() + getPosX();
    }
}
