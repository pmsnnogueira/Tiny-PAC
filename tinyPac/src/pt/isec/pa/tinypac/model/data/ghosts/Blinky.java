package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Ghost;

public class Blinky extends Ghost {


    public Blinky(int posX, int posY){
        super(posX, posY);
    }


    @Override
    public boolean move() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'B';
    }
}
