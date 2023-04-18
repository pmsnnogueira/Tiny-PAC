package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import utils.Obstacles;

public class Portal implements IMazeElement {

    private final char symbol;

    public Portal(){
        this.symbol = Obstacles.PORTAL.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
