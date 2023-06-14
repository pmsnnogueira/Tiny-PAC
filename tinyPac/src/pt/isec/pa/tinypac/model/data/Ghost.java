package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.utils.Position;
import pt.isec.pa.tinypac.utils.Stack;

import java.io.Serial;
import java.io.Serializable;

public abstract class Ghost extends GameObjects implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Boolean locked;
    private Position currentPosition;
    private final Position initialPosition;
    private Stack<Position> movements;
    private Boolean vulnerable;

    private Boolean dead;
    private static final Integer DEFAULT_GHOST_POINTS = 50;
    private Integer ghostPoints;

    public Integer DEFAULT_TICKS_TO_MOVE_GHOST = 6;
    private Integer ticksToMove;

    public Ghost(Game game, int posX , int posY){
        super(game);
        this.locked = true;
        this.initialPosition = new Position(posX,posY);
        this.currentPosition = new Position(posX,posY);
        this.movements = new Stack<>();
        this.vulnerable = false;
        this.ticksToMove = DEFAULT_TICKS_TO_MOVE_GHOST;
        this.dead = false;
        this.ghostPoints = 50;
    }

    public void setPos(int posX , int posY){
        this.currentPosition.setPos(posX,posY);
    }

    public void unlockGhost(){
        this.locked = false;
        this.vulnerable = false;
        this.movements.clear();
        this.currentPosition = new Position(initialPosition);
        this.dead = false;
    }

    public Boolean getDead() {
        return dead;
    }

    public void setDead(Boolean dead) {
        this.dead = dead;
    }

    public void setTicksToMove(Integer ticksToMove) {
        this.ticksToMove = ticksToMove;
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

    public boolean isMovementsEmpty(){
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

    public void incGhostPoints(){
        this.ghostPoints += 50;
    }

    public void setGhostPoints(Integer ghostPoints) {
        this.ghostPoints = ghostPoints;
    }

    public Integer getGhostPoints() {
        return ghostPoints;
    }

    public void reset() {

        this.currentPosition = new Position(initialPosition);
        this.movements.clear();
        this.dead = false;
        this.vulnerable = false;
        this.locked = true;
        setTicksToMove(DEFAULT_TICKS_TO_MOVE_GHOST);
        setGhostPoints(DEFAULT_GHOST_POINTS);
    }

    public int getTicksToMove() {
        return ticksToMove;
    }

    public boolean isInInicialPosition() {
        return currentPosition.equals(initialPosition);
    }

    public void changeToUnVulnerable() {
        setTicksToMove(DEFAULT_TICKS_TO_MOVE_GHOST);
        unlockGhost();
        //reset();
    }
}