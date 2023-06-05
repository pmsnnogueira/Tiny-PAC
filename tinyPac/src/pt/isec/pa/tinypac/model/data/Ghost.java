package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.utils.Position;
import pt.isec.pa.tinypac.utils.Stack;

public abstract class Ghost extends GameObjects{
    private Boolean locked;
    private Position currentPosition;
    private final Position initialPosition;
    private Stack<Position> movements;
    private Boolean vulnerable;
    private static final char SYMBOL = 'G';
    public static final int GHOST_POINTS = 200;

    private Integer ticksToMove;

    public Ghost(Game game, int posX , int posY){
        super(game);
        this.locked = true;
        this.initialPosition = new Position(posX,posY);
        this.currentPosition = new Position(posX,posY);
        this.movements = new Stack();
        this.vulnerable = false;
        this.ticksToMove = 500;
    }

    public void setPos(int posX , int posY){
        this.currentPosition.setPos(posX,posY);
    }

    public void unlockGhost(){
        this.locked = false;
        this.vulnerable = false;
        //this.currentPosition = initialPosition;
    }

    public Boolean getVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(Boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public Integer getInitialPositionX() {
        return this.initialPosition.getPosX();
    }

    public Integer getInitialPositionY() {
        return this.initialPosition.getPosY();
    }

    public void pushLastPosition(Integer posX, Integer posY){
        this.movements.push(new Position(posX, posY));
    }
    public Position popLastPosition(){
        return this.movements.pop();
    }

    public boolean isLastPositionEmpty(){
        return this.movements.empty();
    }

    public Integer getPosX() {
        return this.currentPosition.getPosX();
    }

    public Integer getPosY() {
        return this.currentPosition.getPosY();
    }

    public Boolean getLocked() {
        return locked;
    }
    public void setPosX(Integer posX) {
        this.currentPosition.setPosX(posX);
    }

    public void setLocked(Boolean lock) {
        this.locked = lock;
    }

    @Override
    public char getSymbol() {
        return 0;
    }

    public void reset() {
        this.vulnerable = false;
        this.locked = true;
        this.movements.clear();
        this.currentPosition = new Position(initialPosition);
    }

    public int getTicksToMove() {
        return ticksToMove;
    }
}