package pt.isec.pa.tinypac.model.data;

public abstract class Ghost extends GameObjects{
    private Boolean locked;
    private Integer posX;
    private Integer posY;

    private Integer initialPositionX;
    private Integer initialPositionY;
    private static final char SYMBOL = 'G';

    public static final int GHOST_POINTS = 200;


    public Ghost(Game game, int posX , int posY){
        super(game);
        this.locked = true;
        this.posX = posX;
        this.posY = posY;
        this.initialPositionX = posX;
        this.initialPositionY = posY;
    }

    public void setPos(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void reset(){
        this.locked = true;
        this.posX = initialPositionX;
        this.posY = initialPositionY;
    }

    public Integer getInitialPositionX() {
        return initialPositionX;
    }

    public Integer getInitialPositionY() {
        return initialPositionY;
    }

    public Integer getPosX() {
        return posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public Boolean getLocked() {
        return locked;
    }
    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }
    public void setLocked(Boolean lock) {
        this.locked = lock;
    }


    @Override
    public char getSymbol() {
        return 0;
    }
}