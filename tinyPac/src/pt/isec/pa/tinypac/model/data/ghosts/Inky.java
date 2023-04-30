package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.Maze;

public class Inky extends Ghost{

    public Inky(Game game,int posX, int posY){
        super(game,posX, posY);
    }

    @Override
    public char getSymbol() {
        return 'I';
    }

    @Override
    public boolean evolve() {
        return false;
    }
}
