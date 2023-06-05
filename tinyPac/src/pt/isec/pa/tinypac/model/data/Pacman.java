package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.PacmanPosition;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serial;
import java.io.Serializable;

public class Pacman extends GameObjects implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private PacmanPosition pacmanPosition;

    private PacmanPosition initialPositon;
    private boolean power;

    private Integer ticksToMove;

    public Pacman(Game game,Integer posX, Integer posY){
        super(game);
        this.pacmanPosition = new PacmanPosition(posX, posY, game.getMazeRows(), game.getMazeColumns());
        this.initialPositon = new PacmanPosition(pacmanPosition);
        this.power = false;
        this.ticksToMove = 75;
    }

    /*public Pacman(Pacman pacman){
        super(getGame());
        this.pacmanPosition = pacman.getCurrentPosition();
        this.initialPositon = pacman.getInitialPositon();
        this.power = pacman.getPower();
    }*/

    public PacmanPosition getInitialPositon() {
        return initialPositon;
    }

    public PacmanPosition getCurrentPosition(){
        return new PacmanPosition(pacmanPosition);
    }

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

        if(element.getSymbol() == Obstacles.WALL.getSymbol()){
            return false;
        }
        if(element.getSymbol() == Obstacles.WARP.getSymbol()){
            Position pos = game.getRandomWarpPosition();
        }
        pacmanPosition.setPos(nextDirections[0] , nextDirections[1]);

        return true;
    }

    @Override
    public void returnToBase() {
        return;
    }

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

    public int getPosX(){
        return pacmanPosition.getPosX();
    }

    public int getPosY(){
        return pacmanPosition.getPosY();
    }

    public boolean getPower(){
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    @Override
    public char getSymbol() {
        return Obstacles.PACMAN.getSymbol();
    }

    public void reset() {
        this.pacmanPosition = new PacmanPosition(initialPositon);
    }

    public Direction getDirection() {
        return pacmanPosition.getDirection();
    }

    public Integer getTicksToMove() {
        return ticksToMove;
    }
}
