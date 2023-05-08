package pt.isec.pa.tinypac.utils;

public class Position {
    private int posX;
    private int posY;

    public Position(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public Position(PacmanPosition pacmanPosition){
        this.posX = pacmanPosition.getPosX();
        this.posY = pacmanPosition.getPosY();
    }

    public void setPos(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
}
