package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

public class Warp implements IMazeElement {

    private final char symbol;

    public Warp(){
        this.symbol = Obstacles.WARP.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
