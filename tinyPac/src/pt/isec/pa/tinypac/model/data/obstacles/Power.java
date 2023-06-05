package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.io.Serial;
import java.io.Serializable;

public class Power implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    public static final int POWER_POINTS = 50;
    public static final long POWER_TIME = 5000;


    public Power(){
        this.symbol = Obstacles.POWER.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
