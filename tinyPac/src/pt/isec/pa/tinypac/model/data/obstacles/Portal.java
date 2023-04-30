package pt.isec.pa.tinypac.model.data.obstacles;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import utils.Obstacles;

public class Portal implements IMazeElement {

    private final char symbol;

    private final int posX;
    private final int posY;

    public Portal(int posX, int posY){
        this.symbol = Obstacles.PORTAL.getSymbol();
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
