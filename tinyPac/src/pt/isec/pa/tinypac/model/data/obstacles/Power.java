package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

public class Power implements IMazeElement {

    private final char symbol;

    public Power(){
        this.symbol = Obstacles.POWER.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
