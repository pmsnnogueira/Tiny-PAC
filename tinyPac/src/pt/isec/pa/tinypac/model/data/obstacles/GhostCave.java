package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import utils.Obstacles;

public class GhostCave implements IMazeElement {

    private final char symbol;

    public GhostCave(){
        this.symbol = Obstacles.GHOST_CAVE.getSymbol();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
