package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.PacmanPosition;
import pt.isec.pa.tinypac.utils.Position;

public class Pacman extends GameObjects{
    private PacmanPosition pacmanPosition;
    private boolean power;

    public Pacman(Game game,Integer posX, Integer posY){
        super(game);
        this.power = false;
        this.pacmanPosition = new PacmanPosition(posX, posY, game.getMazeRows(), game.getMazeColumns());
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
        return '*';
    }
}
