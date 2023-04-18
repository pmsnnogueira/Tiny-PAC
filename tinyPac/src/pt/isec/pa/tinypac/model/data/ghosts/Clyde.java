package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Ghost;

public class Clyde extends Ghost {

    public Clyde(int posX, int posY){
        super(posX, posY);
    }


    @Override
    public boolean move() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'C';
    }
}
