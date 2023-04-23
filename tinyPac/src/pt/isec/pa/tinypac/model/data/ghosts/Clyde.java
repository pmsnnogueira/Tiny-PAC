package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.Maze;

public class Clyde extends Ghost {

    public Clyde(Game game, int posX, int posY){
        super(game,posX,posY);
    }

    @Override
    public char getSymbol() {
        return 'C';
    }

    @Override
    public boolean evolve() {
        return false;
    }
}
