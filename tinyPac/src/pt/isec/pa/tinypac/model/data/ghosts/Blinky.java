package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.Main;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.Maze;
import utils.Obstacles;

import java.util.ArrayList;
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
            ArrayList<Integer> possibleDirections = new ArrayList(getValidDirections(maze , direction));
            for (int i = 0; i < possibleDirections.size(); i++) {
                switch (possibleDirections.get(i)) {
                    case TOP -> System.out.println("Top");
                    case BOTTOM -> System.out.println("Bottom");
                    case LEFT -> System.out.println("Left");
                    case RIGHT -> System.out.println("Right");
                }
            }
            System.out.println(possibleDirections.size());
            if(possibleDirections.size() == 0){
                System.out.println("Zero movimentos");
                if(direction == TOP)
                    direction = BOTTOM;
                else if(direction == BOTTOM)
                    direction = TOP;
                else if(direction == RIGHT)
                    direction = LEFT;
                else if(direction == LEFT)
                    direction = RIGHT;

            }else {
                direction = possibleDirections.get(new Random().nextInt(possibleDirections.size()));
                switch (direction) {
                    case TOP -> System.out.println("New Direction: Top");
                    case BOTTOM -> System.out.println("New Direction: Bottom");
                    case LEFT -> System.out.println("New Direction: Left");
                    case RIGHT -> System.out.println("New Direction: RIGHT");
                }
            }
           // return false;
        }


        switch (direction) {
            case TOP -> nextY--;
            case BOTTOM -> nextY++;
            case LEFT -> nextX--;
            case RIGHT -> nextX++;
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
            if(top.getSymbol() == Obstacles.WALL.getSymbol() ||
                bottom.getSymbol() == Obstacles.WALL.getSymbol() ||
                left.getSymbol() != Obstacles.WALL.getSymbol() ||
                right.getSymbol() != Obstacles.WALL.getSymbol()){
                return true;
            }
        }else if(direction == RIGHT || direction == LEFT){
            if(right.getSymbol() == Obstacles.WALL.getSymbol() ||
                left.getSymbol() == Obstacles.WALL.getSymbol() ||
                top.getSymbol() != Obstacles.WALL.getSymbol()  ||
                bottom.getSymbol() != Obstacles.WALL.getSymbol()){
                return true;
            }
        }

        return false;
    }

    private ArrayList<Integer> getValidDirections(Maze maze , Integer direction) {
       ArrayList<Integer> possibleDirections = new ArrayList<>();

        if(direction == TOP){
            // verifica se pode ir para cima
            if (maze.get(getPosY() - 1,getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(TOP);
            }
            // verifica se pode ir para a direita
            if (maze.get(getPosY(), getPosX() + 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(RIGHT);
            }
            // verifica se pode ir para a esquerda
            if (maze.get(getPosY(), getPosX() - 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(LEFT);
            }
        }else if(direction == LEFT){
            // verifica se pode ir para a esquerda
            if (maze.get(getPosY(), getPosX() - 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(LEFT);
            }
            // verifica se pode ir para cima
            if (maze.get(getPosY() - 1,getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(TOP);
            }
            IMazeElement bottom = maze.get(getPosY() + 1,getPosX());
            // verifica se pode ir para baixo
            if (bottom.getSymbol() != Obstacles.WALL.getSymbol() &&             //Testar este
                bottom.getSymbol() != Obstacles.GHOST_CAVE.getSymbol() ||
                bottom.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(BOTTOM);
            }
        }
        else if(direction == RIGHT){
            // verifica se pode ir para a direita
            if (maze.get(getPosY(), getPosX() + 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(RIGHT);
            }
            // verifica se pode ir para cima
            if (maze.get(getPosY() - 1,getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(TOP);
            }
            // verifica se pode ir para baixo
            if (maze.get(getPosY() + 1 , getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(BOTTOM);
            }
        }else if(direction == BOTTOM){
            // verifica se pode ir para a esquerda
            if (maze.get(getPosY(), getPosX() - 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(LEFT);
            }
            // verifica se pode ir para a direita
            if (maze.get(getPosY(), getPosX() + 1).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(RIGHT);
            }
            // verifica se pode ir para baixo
            if (maze.get(getPosY() + 1 , getPosX()).getSymbol() != Obstacles.WALL.getSymbol()) {
                possibleDirections.add(BOTTOM);
            }
        }

        // retorna um novo array com as direções possíveis
        return possibleDirections;
    }


    @Override
    public char getSymbol() {
        return 'B';
    }

}
