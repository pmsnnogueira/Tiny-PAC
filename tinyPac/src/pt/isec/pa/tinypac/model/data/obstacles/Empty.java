package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class Empty implements IMazeElement {

    private final char symbol;

    public Empty(){
        this.symbol = ' ';
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
