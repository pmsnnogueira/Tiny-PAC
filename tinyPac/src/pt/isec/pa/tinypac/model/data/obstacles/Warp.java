package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serial;
import java.io.Serializable;

public class Warp implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    private Position position;

    public Warp(Integer posX, Integer posY){
        this.symbol = Obstacles.WARP.getSymbol();
        this.position = new Position(posX, posY);
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public Integer getPositionX() {
        return position.getPosX();
    }

    public Integer getPositionY() {
        return position.getPosY();
    }
}
