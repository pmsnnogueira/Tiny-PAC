package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.Main;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.Maze;
import utils.Obstacles;

import java.util.Random;

public class Blinky extends Ghost{

    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    private static final int BOTTOM = 4;

    private int direction;


    public Blinky(Game game,int posX, int posY){
        super(game,posX, posY);
        this.direction = TOP;
    }

    @Override
    public boolean evolve(){
        Maze maze = game.getMaze();
        IMazeElement top = maze.get(getPosY() - 1 ,getPosX());
        IMazeElement left = maze.get(getPosY(), getPosX() - 1);
        IMazeElement right = maze.get(getPosY(), getPosX() + 1);
        IMazeElement bottom = maze.get(getPosY() + 1, getPosX());
        float random = (float) Math.random();
        int nextX = getPosX();
        int nextY = getPosY();


        switch (direction){
            case TOP :
                --nextY;
                break;
            case RIGHT:
                ++nextX;
                break;
            case LEFT:
                --nextX;
                break;
            case BOTTOM:
                ++nextY;
                break;
        }

        //Chegar a uma interseção
        if(getValidDirections(maze,direction).length > 1){
            int[] possibleDirections = getValidDirections(maze, direction);
            if(possibleDirections.length == 0)
                return false;
            direction = possibleDirections[new Random().nextInt(possibleDirections.length)];
        }else{
            setPos(nextX , nextY);
        }

        return false;
    }

    private boolean mazeIntersection(Maze maze) {

        IMazeElement top = maze.get(getPosY() - 1 ,getPosX());
        IMazeElement left = maze.get(getPosY(), getPosX() - 1);
        IMazeElement right = maze.get(getPosY(), getPosX() + 1);
        IMazeElement bottom = maze.get(getPosY() + 1, getPosX());

        if(top.getSymbol() != Obstacles.WALL.getSymbol()){
            if(left.getSymbol() != Obstacles.WALL.getSymbol() &&
                right.getSymbol() != Obstacles.WALL.getSymbol() &&
                    bottom.getSymbol() != Obstacles.WALL.getSymbol()
            )
                return true;
        }
        if(right.getSymbol() != Obstacles.WALL.getSymbol()){
            if(top.getSymbol() != Obstacles.WALL.getSymbol() ||
                    left.getSymbol() != Obstacles.WALL.getSymbol() ||
                    bottom.getSymbol() != Obstacles.WALL.getSymbol()
            )
                return true;
        }
        if(left.getSymbol() != Obstacles.WALL.getSymbol()){
            if(top.getSymbol() != Obstacles.WALL.getSymbol() ||
                    right.getSymbol() != Obstacles.WALL.getSymbol() ||
                    bottom.getSymbol() != Obstacles.WALL.getSymbol()
            )
                return true;
        }
        if(bottom.getSymbol() != Obstacles.WALL.getSymbol()){
            if(top.getSymbol() != Obstacles.WALL.getSymbol() ||
                    left.getSymbol() != Obstacles.WALL.getSymbol() ||
                    right.getSymbol() != Obstacles.WALL.getSymbol()
            )
                return true;
        }

        return false;
    }

    private int[] getValidDirections(Maze maze , Integer direction) {
        int[] possibleDirections = new int[4];
        int count = 0;

        // verifica se pode ir para a direita
        if (maze.get(getPosY(), getPosX() + 1).getSymbol() != Obstacles.WALL.getSymbol() && direction != RIGHT) {
            possibleDirections[count++] = RIGHT;
        }
        // verifica se pode ir para baixo
        if (maze.get(getPosY() + 1 , getPosX()).getSymbol() != Obstacles.WALL.getSymbol() && direction != BOTTOM) {
            possibleDirections[count++] = BOTTOM;
        }
        // verifica se pode ir para a esquerda
        if (maze.get(getPosY(), getPosX() - 1).getSymbol() != Obstacles.WALL.getSymbol() && direction != LEFT) {
            possibleDirections[count++] = LEFT;
        }
        // verifica se pode ir para cima
        if (maze.get(getPosY() - 1,getPosX()).getSymbol() != Obstacles.WALL.getSymbol() && direction != TOP) {
            possibleDirections[count++] = TOP;
        }

        // retorna um novo array com as direções possíveis
        return count == 0 ? new int[]{} : java.util.Arrays.copyOfRange(possibleDirections, 0, count);
    }


    @Override
    public char getSymbol() {
        return 'B';
    }

}
