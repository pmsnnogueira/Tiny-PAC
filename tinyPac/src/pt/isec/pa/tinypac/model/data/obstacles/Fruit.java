package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.io.Serial;
import java.io.Serializable;

public class Fruit implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    public static final int FRUIT_POINTS = 25;

    public Fruit(){
        this.symbol = Obstacles.FRUIT.getSymbol();
    }


    @Override
    public char getSymbol() {
        return symbol;
    }
}
