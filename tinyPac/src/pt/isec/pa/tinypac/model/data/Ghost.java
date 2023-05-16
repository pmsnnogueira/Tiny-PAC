package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.utils.GhostPosition;
import pt.isec.pa.tinypac.utils.Stack;



public abstract class Ghost extends GameObjects{
    private Boolean locked;
    private GhostPosition currentPosition;
    private final GhostPosition initialPosition;
    private Stack<GhostPosition> movements;
    private Boolean returnToBase;
    private static final char SYMBOL = 'G';

    public static final int GHOST_POINTS = 200;


    public Ghost(Game game, int posX , int posY){
        super(game);
        this.locked = true;
        this.initialPosition = new GhostPosition(posX,posY);
        this.currentPosition = new GhostPosition(posX,posY);
        this.movements = new Stack();
        this.returnToBase = false;
    }

    public void setPos(int posX , int posY){
        this.currentPosition.setPos(posX,posY);
    }

    public void unlockGhost(){
        this.locked = false;
        this.returnToBase = false;
        //this.currentPosition = initialPosition;
    }

    public Boolean getReturnToBase() {
        return returnToBase;
    }

    public void setReturnToBase(Boolean returnToBase) {
        this.returnToBase = returnToBase;
    }

    public Integer getInitialPositionX() {
        return this.initialPosition.getPosX();
    }

    public Integer getInitialPositionY() {
        return this.initialPosition.getPosY();
    }

    public void pushLastPosition(Integer posX, Integer posY){
        this.movements.push(new GhostPosition(posX, posY));
    }
    public GhostPosition popLastPosition(){
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
}