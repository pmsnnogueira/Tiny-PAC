package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import utils.Obstacles;

public class Fruit implements IMazeElement {

    private final char symbol;

    public Fruit(){
        this.symbol = Obstacles.FRUIT.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
