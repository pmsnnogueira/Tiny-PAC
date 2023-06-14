package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;

import java.io.Serial;
import java.io.Serializable;

public class Empty implements IMazeElement , Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final char symbol;

    public Empty(){
        this.symbol = ' ';
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
