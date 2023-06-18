package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.utils.Position;
import pt.isec.pa.tinypac.utils.Stack;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Ghost class represents a ghost in the game.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
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

    /**
     * Constructs a Ghost object.
     * @param game The game instance.
     * @param posX The initial X position of the ghost.
     * @param posY The initial Y position of the ghost.
     */
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

    /**
     * Sets the position of the ghost.
     * @param posX The X position to be set.
     * @param posY The Y position to be set.
     */
    public void setPos(int posX , int posY){
        this.currentPosition.setPos(posX,posY);
    }

    /**
     * Unlocks the ghost, allowing it to move.
     */
    public void unlockGhost(){
        this.locked = false;
        this.vulnerable = false;
        this.movements.clear();
        this.currentPosition = new Position(initialPosition);
        this.dead = false;
    }

    /**
     * Retrieves the dead status of the ghost.
     * @return True if the ghost is dead, false otherwise.
     */
    public Boolean getDead() {
        return dead;
    }

    /**
     * Sets the dead status of the ghost.
     * @param dead The dead status to be set.
     */
    public void setDead(Boolean dead) {
        this.dead = dead;
    }

    /**
     * Sets the number of ticks to move for the ghost.
     * @param ticksToMove The number of ticks to move to be set.
     */
    public void setTicksToMove(Integer ticksToMove) {
        this.ticksToMove = ticksToMove;
    }

    /**
     * Retrieves the vulnerable status of the ghost.
     * @return True if the ghost is vulnerable, false otherwise.
     */
    public Boolean getVulnerable() {
        return vulnerable;
    }

    /**
     * Sets the vulnerable status of the ghost.
     * @param vulnerable The vulnerable status to be set.
     */
    public void setVulnerable(Boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    /**
     * Pushes the last position onto the movements stack.
     * @param posX The X position to be pushed.
     * @param posY The Y position to be pushed.
     */
    public void pushLastPosition(Integer posX, Integer posY){
        this.movements.push(new Position(posX, posY));
    }

    /**
     * Pops the last position from the movements stack.
     * @return The last position popped from the stack.
     */
    public Position popLastPosition(){
        return this.movements.pop();
    }

    /**
     * Checks if the movements stack is empty.
     * @return True if the movements stack is empty, false otherwise.
     */
    public boolean isMovementsEmpty(){
        return this.movements.empty();
    }

    /**
     * Retrieves the X position of the ghost.
     * @return The X position of the ghost.
     */
    public Integer getPosX() {
        return this.currentPosition.getPosX();
    }

    /**
     * Retrieves the Y position of the ghost.
     * @return The Y position of the ghost.
     */
    public Integer getPosY() {
        return this.currentPosition.getPosY();
    }

    /**
     * Retrieves the locked status of the ghost.
     * @return True if the ghost is locked, false otherwise.
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * Sets the X position of the ghost.
     * @param posX The X position to be set.
     */
    public void setPosX(Integer posX) {
        this.currentPosition.setPosX(posX);
    }

    /**
     * Sets the locked status of the ghost.
     * @param lock The locked status to be set.
     */
    public void setLocked(Boolean lock) {
        this.locked = lock;
    }

    @Override
    public char getSymbol() {
        return 0;
    }

    /**
     * Increases the ghost points by the default points.
     */
    public void incGhostPoints(){
        this.ghostPoints += 50;
    }

    /**
     * Sets the ghost points.
     * @param ghostPoints The ghost points to be set.
     */
    public void setGhostPoints(Integer ghostPoints) {
        this.ghostPoints = ghostPoints;
    }

    /**
     * Retrieves the ghost points.
     * @return The ghost points.
     */
    public Integer getGhostPoints() {
        return ghostPoints;
    }

    /**
     * Resets the ghost to its initial state.
     */
    public void reset() {

        this.currentPosition = new Position(initialPosition);
        this.movements.clear();
        this.dead = false;
        this.vulnerable = false;
        this.locked = true;
        setTicksToMove(DEFAULT_TICKS_TO_MOVE_GHOST);
        setGhostPoints(DEFAULT_GHOST_POINTS);
    }


    /**
     * Retrieves the number of ticks to move for the ghost.
     * @return The number of ticks to move for the ghost.
     */
    public int getTicksToMove() {
        return ticksToMove;
    }

    /**
     * Checks if the ghost is in its initial position.
     * @return True if the ghost is in its initial position, false otherwise.
     */
    public boolean isInInicialPosition() {
        return currentPosition.equals(initialPosition);
    }

    /**
     * Changes the ghost to the unvulnerable state.
     */
    public void changeToUnVulnerable() {
        setTicksToMove(DEFAULT_TICKS_TO_MOVE_GHOST);
        unlockGhost();
        //reset();
    }
}