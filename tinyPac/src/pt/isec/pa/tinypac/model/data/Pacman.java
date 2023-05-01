package pt.isec.pa.tinypac.model.data;

import utils.Direction;
import utils.Obstacles;
import utils.Position;

public class Pacman extends GameObjects{
    private Position position;
    private boolean power;

    public Pacman(Game game,Integer posX, Integer posY){
        super(game);
        this.power = false;
        this.position = new Position(posX, posY, game.getMazeRows(), game.getMazeColumns());
    }

    public Position getCurrentPosition(){
        return new Position(position);
    }


    @Override
    public boolean evolve() {

        //Movimentacao do pacman

        Maze maze = game.getMaze();
        if(maze == null)
            return false;

        int[] nextDirections = position.getNextPosition();//Next x and y
        IMazeElement element = maze.get(nextDirections[1], nextDirections[0]);
        if(element == null) {
            position.setPos(nextDirections[0], nextDirections[1]);
            return true;
        }

        if(element.getSymbol() == Obstacles.WALL.getSymbol()){
            return false;
        }
        position.setPos(nextDirections[0] , nextDirections[1]);

        return true;
    }

    public boolean setDirection(Direction direction) {

        Position aux = new Position(getPosX() , getPosY() , game.getMazeRows() , game.getMazeColumns());
        aux.setDirection(direction);
        int[] nextPos = aux.getNextPosition();
        Maze maze = game.getMaze();

        IMazeElement mazeElement = maze.get(nextPos[1] , nextPos[0]);


        if(verifyElementObstacle(mazeElement)){
            position.setDirection(direction);
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
        return position.getPosX();
    }

    public int getPosY(){
        return position.getPosY();
    }

    @Override
    public char getSymbol() {
        return '*';
    }
}
