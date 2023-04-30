package pt.isec.pa.tinypac.model.data;

public abstract class Ghost extends GameObjects{
    private Boolean lock;
    private Integer posX;
    private Integer posY;

    private static final char SYMBOL = 'G';

    public Ghost(Game game, int posX , int posY){
        super(game);
        this.lock = true;
        this.posX = posX;
        this.posY = posY;
    }

    public void setPos(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public Integer getPosX() {
        return posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public Boolean getLock() {
        return lock;
    }
    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }
    public void setLock(Boolean lock) {
        this.lock = lock;
    }


    @Override
    public char getSymbol() {
        return 0;
    }
}