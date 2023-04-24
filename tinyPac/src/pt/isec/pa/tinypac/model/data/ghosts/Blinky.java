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
        int nextX = getPosX();
        int nextY = getPosY();



        if(cruzamento(maze,direction)) {
            int[] possibleDirections = getValidDirections(maze, direction);
            for (int i = 0; i < possibleDirections.length; i++) {
                switch (possibleDirections[i]) {
                    case TOP -> System.out.println("Top");
                    case BOTTOM -> System.out.println("Bottom");
                    case LEFT -> System.out.println("Left");
                    case RIGHT -> System.out.println("Right");
                }
            }

            direction = possibleDirections[new Random().nextInt(possibleDirections.length)];
            switch (direction) {
                case TOP -> System.out.println("New Direction: Top");
                case BOTTOM -> System.out.println("New Direction: Bottom");
                case LEFT -> System.out.println("New Direction: Left");
                case RIGHT -> System.out.println("New Direction: RIGHT");
            }

           // return false;
        }


        switch (direction){
            case TOP :
                nextY--;
                break;
            case BOTTOM:
                nextY++;
                break;
            case LEFT:
                nextX--;
                break;
            case RIGHT:
                nextX++;
                break;
        }

        setPos(nextX , nextY);
        return false;
    }

    private boolean cruzamento(Maze maze, int direction){

        IMazeElement top = maze.get(getPosY() - 1 ,getPosX());
        IMazeElement left = maze.get(getPosY(), getPosX() - 1);
        IMazeElement right = maze.get(getPosY(), getPosX() + 1);
        IMazeElement bottom = maze.get(getPosY() + 1, getPosX());

        if(direction == TOP || direction == BOTTOM){
            if(left.getSymbol() != Obstacles.WALL.getSymbol() ||
                    right.getSymbol() != Obstacles.WALL.getSymbol()){
                return true;
            }
        }
        if(direction == RIGHT || direction == LEFT){
            if(top.getSymbol() != Obstacles.WALL.getSymbol() ||
                    bottom.getSymbol() != Obstacles.WALL.getSymbol()){
                return true;
            }
        }

        return false;
    }

    private int[] getValidDirections(Maze maze , Integer direction) {
        int[] possibleDirections = new int[4];
        int count = 0;


        if(direction == TOP){
            // verifica se pode ir para cima
            if (maze.get(getPosY() - 1,getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = TOP;
            }
            // verifica se pode ir para a direita
            if (maze.get(getPosY(), getPosX() + 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = RIGHT;
            }
            // verifica se pode ir para a esquerda
            if (maze.get(getPosY(), getPosX() - 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = LEFT;
            }
        }else if(direction == LEFT){
            // verifica se pode ir para a esquerda
            if (maze.get(getPosY(), getPosX() - 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = LEFT;
            }
            // verifica se pode ir para cima
            if (maze.get(getPosY() - 1,getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = TOP;
            }
            // verifica se pode ir para baixo
            if (maze.get(getPosY() + 1 , getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = BOTTOM;
            }
        }
        else if(direction == RIGHT){
            // verifica se pode ir para a direita
            if (maze.get(getPosY(), getPosX() + 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = RIGHT;
            }
            // verifica se pode ir para cima
            if (maze.get(getPosY() - 1,getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = TOP;
            }
            // verifica se pode ir para baixo
            if (maze.get(getPosY() + 1 , getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = BOTTOM;
            }
        }else if(direction == BOTTOM){
            // verifica se pode ir para a esquerda
            if (maze.get(getPosY(), getPosX() - 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = LEFT;
            }
            // verifica se pode ir para a direita
            if (maze.get(getPosY(), getPosX() + 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = RIGHT;
            }
            // verifica se pode ir para baixo
            if (maze.get(getPosY() + 1 , getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections[count++] = BOTTOM;
            }
        }

        // retorna um novo array com as direções possíveis
        return count == 0 ? new int[]{} : java.util.Arrays.copyOfRange(possibleDirections, 0, count);
    }


    @Override
    public char getSymbol() {
        return 'B';
    }

}
