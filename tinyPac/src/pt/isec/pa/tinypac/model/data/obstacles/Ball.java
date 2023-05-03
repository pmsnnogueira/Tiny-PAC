package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

public class Ball implements IMazeElement {

    private final char symbol;

    public static final int BALL_POINTS = 10;

    public Ball(){
        this.symbol = Obstacles.BALL.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
