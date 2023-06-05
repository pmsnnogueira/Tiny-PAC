package pt.isec.pa.tinypac.utils;

import javafx.geometry.Pos;

import java.io.Serial;
import java.io.Serializable;

public class Position implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int posX;
    private int posY;

    public Position(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public Position(Position position){
        this.posX = position.getPosX();
        this.posY = position.getPosY();
    }

    public void setPos(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() == obj.getClass()) {
            return (((Position) obj).getPosX() == this.getPosX() && ((Position) obj).getPosY() == this.getPosY());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getPosY() + getPosX();
    }
}
