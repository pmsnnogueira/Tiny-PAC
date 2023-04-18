package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class Wall implements IMazeElement {

    private char symbol;

    public Wall(){
        this.symbol = 'X';
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
