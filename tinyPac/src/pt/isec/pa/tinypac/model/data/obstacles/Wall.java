package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

public class Wall implements IMazeElement {

    private final char symbol;

    public Wall(){
        this.symbol = Obstacles.WALL.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
