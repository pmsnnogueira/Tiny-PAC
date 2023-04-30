package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import utils.Obstacles;

public class PacmanInitialPosition implements IMazeElement {

    private final char symbol;

    public PacmanInitialPosition(){
        this.symbol = Obstacles.PACMAN_INITIAL_POSITION.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
