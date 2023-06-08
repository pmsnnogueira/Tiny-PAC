package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;

import java.io.Serial;
import java.io.Serializable;

public class Clyde extends Ghost implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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

    @Override
    public void returnToBase() {
        return;
    }
}
