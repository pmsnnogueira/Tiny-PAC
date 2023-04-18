package pt.isec.pa.tinypac.model.data;

public class Pacman {
    private int posX;
    private int posY;
    private int lives;
    private boolean power;

    public Pacman(){
        this.posX = 0;
        this.posY = 0;
        this.lives = 3;
        this.power = false;
    }

    public void setPos(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }
}
