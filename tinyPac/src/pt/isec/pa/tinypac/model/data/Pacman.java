package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.PacmanPosition;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Pacman class represents the Pacman in the game.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Pacman extends GameObjects implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private PacmanPosition pacmanPosition;

    private PacmanPosition initialPositon;
    private boolean power;

    private Integer ticksToMove;

    /**
     * Constructs a Pacman object.
     * @param game The game instance.
     * @param posX The initial X position of Pacman.
     * @param posY The initial Y position of Pacman.
     */
    public Pacman(Game game,Integer posX, Integer posY){
        super(game);
        this.pacmanPosition = new PacmanPosition(posX, posY, game.getMazeRows(), game.getMazeColumns());
        this.initialPositon = new PacmanPosition(pacmanPosition);
        this.power = false;
        this.ticksToMove = 2;
    }

    /*public Pacman(Pacman pacman){
        super(getGame());
        this.pacmanPosition = pacman.getCurrentPosition();
        this.initialPositon = pacman.getInitialPositon();
        this.power = pacman.getPower();
    }*/

    /**
     * Retrieves the current position of Pacman.
     * @return The current position of Pacman.
     */
    public PacmanPosition getCurrentPosition(){
        return new PacmanPosition(pacmanPosition);
    }

    /**
     * Move the pacman
     * @return true if the pacman was moved, false otherwise
     */
    @Override
    public boolean evolve() {

        //Movimentacao do pacman
        Maze maze = game.getMaze();
        if(maze == null)
            return false;

        int[] nextDirections = pacmanPosition.getNextPosition();//Next x and y
        IMazeElement element = maze.get(nextDirections[1], nextDirections[0]);

        if(element == null) {
            pacmanPosition.setPos(nextDirections[0], nextDirections[1]);
            return true;
        }

        if(element.getSymbol() == Obstacles.WARP.getSymbol()){      //Teleport
            Position pos = game.getRandomWarpPosition(new Position(nextDirections[0],nextDirections[1]));
            pacmanPosition.setPos(pos.getPosX(), pos.getPosY());
            return true;
        }

        if(element.getSymbol() == Obstacles.WALL.getSymbol() || element.getSymbol() == Obstacles.PORTAL.getSymbol())
            return false;


        pacmanPosition.setPos(nextDirections[0] , nextDirections[1]);

        return true;
    }

    @Override
    public void returnToBase() {
        return;
    }


    /**
     * Sets the direction of Pacman.
     * @param direction The direction to be set.
     * @return True if the direction is valid, false otherwise.
     */
    public boolean setDirection(Direction direction) {

        PacmanPosition aux = new PacmanPosition(getPosX() , getPosY() , game.getMazeRows() , game.getMazeColumns());
        aux.setDirection(direction);
        int[] nextPos = aux.getNextPosition();
        Maze maze = game.getMaze();

        IMazeElement mazeElement = maze.get(nextPos[1] , nextPos[0]);


        if(verifyElementObstacle(mazeElement)){
            pacmanPosition.setDirection(direction);
            return true;
        }

        return false;
    }

    private boolean verifyElementObstacle(IMazeElement mazeElement) {
        if(mazeElement == null)
            return true;

        if(mazeElement.getSymbol() == Obstacles.WALL.getSymbol() || mazeElement.getSymbol() == Obstacles.PORTAL.getSymbol()){
            return false;
        }
        return true;
    }

    /**
     * Retrieves the X position of Pacman.
     * @return The X position of Pacman.
     */
    public int getPosX(){
        return pacmanPosition.getPosX();
    }

    /**
     * Retrieves the Y position of Pacman.
     * @return The Y position of Pacman.
     */
    public int getPosY(){
        return pacmanPosition.getPosY();
    }

    /**
     * Retrieves the power status of Pacman.
     * @return True if Pacman has power, false otherwise.
     */
    public boolean getPower(){
        return power;
    }

    /**
     * Sets the power status of Pacman.
     * @param power The power status to be set.
     */
    public void setPower(boolean power) {
        this.power = power;
    }

    @Override
    public char getSymbol() {
        return Obstacles.PACMAN.getSymbol();
    }

    /**
     * Resets Pacman to its initial position.
     */
    public void reset() {
        this.pacmanPosition = new PacmanPosition(initialPositon);
    }

    /**
     * Retrieves the direction of Pacman.
     * @return The direction of Pacman.
     */
    public Direction getDirection() {
        return pacmanPosition.getDirection();
    }

    /**
     * Retrieves the number of ticks to move for Pacman.
     * @return The number of ticks to move for Pacman.
     */
    public Integer getTicksToMove() {
        return ticksToMove;
    }
}
