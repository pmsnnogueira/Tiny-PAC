package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Ghost;

public class Pinky extends Ghost {

    public Pinky(int posX, int posY){
        super(posX, posY);
    }

    @Override
    public boolean move() {
        return false;
    }


    @Override
    public char getSymbol() {
        return 'G';
    }
}
