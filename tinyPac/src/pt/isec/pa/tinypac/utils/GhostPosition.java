package pt.isec.pa.tinypac.utils;

public class GhostPosition {
    private int posX;
    private int posY;

    public GhostPosition(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
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
