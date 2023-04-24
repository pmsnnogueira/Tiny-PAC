package pt.isec.pa.tinypac.model.data;

public class Pacman extends GameObjects{
    private int posX;
    private int posY;
    private boolean power;

    public Pacman(Game game,Integer posX, Integer posY){
        super(game);
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

    @Override
    public boolean evolve() {
        return false;
    }
}
