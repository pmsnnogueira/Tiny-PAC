package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.io.Serial;
import java.io.Serializable;

public class PacmanInitialPosition implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    public PacmanInitialPosition(){
        this.symbol = Obstacles.PACMAN_INITIAL_POSITION.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
