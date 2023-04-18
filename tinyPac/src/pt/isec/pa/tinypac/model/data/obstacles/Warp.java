package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class Warp implements IMazeElement {

    private char symbol;

    public Warp(){
        this.symbol = 'W';
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
