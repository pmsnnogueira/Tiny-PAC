package pt.isec.pa.tinypac.model.data;

public class Pacman implements IMazeElement{
    private int posX;
    private int posY;
    private boolean power;

    public Pacman(Integer posX, Integer posY){
        this.posX = posX;
        this.posY = posY;
        this.power = false;
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

    @Override
    public char getSymbol() {
        return '*';
    }
}
