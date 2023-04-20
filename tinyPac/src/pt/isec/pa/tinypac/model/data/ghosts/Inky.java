package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.Maze;

public class Inky extends Ghost{

    public Inky(int posX, int posY){
        super(posX, posY);
    }


    @Override
    public boolean move(Maze maze, int numberRows, int numberColumns) {
        return false;
    }


    @Override
    public char getSymbol() {
        return 'I';
    }
}
