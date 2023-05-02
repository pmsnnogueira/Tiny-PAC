package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.obstacles.Portal;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.util.ArrayList;
import java.util.Random;

public class Blinky extends Ghost{

    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    private static final int DOWN = 4;

    private int direction;


    public Blinky(Game game,int posX, int posY){
        super(game,posX, posY);
        this.direction = UP;
    }

    private void printValidPositions(ArrayList<Integer> validDirections){
        System.out.println("\nValid Directions:");
        for(Integer a: validDirections){
            switch (a){
                case UP -> System.out.println("\tUP");
                case DOWN -> System.out.println("\tDOWN");
                case LEFT -> System.out.println("\tLEFT");
                case RIGHT -> System.out.println("\tRIGHT");
            }
        }
    }

    @Override
    public boolean evolve(){
        Maze maze = game.getMaze();

        if(cruzamento(maze, direction)){
            //Mudar de direcao
            ArrayList<Integer> validDirections = new ArrayList<>(getValidDirections(maze));
            if(validDirections.size() > 0){

                //Mudar aqui a direcao do fantasma
                printValidPositions(validDirections);

                //Escolher qual a melhor para ele e a aleatoriedade
            }


        }

        move(maze, direction);



        return false;
    }

    private boolean move(Maze maze, int direction){

        int nextPosX = getPosX();
        int nextPosY = getPosY();

        switch (direction){
            case UP -> nextPosY--;
            case DOWN -> nextPosY++;
            case LEFT -> nextPosX--;
            case RIGHT -> nextPosX++;
        }

        IMazeElement mazeElement = maze.get(nextPosY, nextPosX);
        if(mazeElement == null || mazeElement.getSymbol() != Obstacles.WALL.getSymbol()){
            setPos(nextPosX, nextPosY);
            return true;
        }

        return false;
    }

    private boolean cruzamento(Maze maze, int direction){

        IMazeElement top = maze.get(getPosY() - 1 ,getPosX());
        IMazeElement left = maze.get(getPosY(), getPosX() - 1);
        IMazeElement right = maze.get(getPosY(), getPosX() + 1);
        IMazeElement down = maze.get(getPosY() + 1, getPosX());

        if(right == null || left == null) {           //Há caminho para a direita ou para a esquerda
            if(top == null || down == null)
                return true;                          //Há um cruzamento
            return false;
        }

        if(direction == UP || direction == DOWN){
            if(top.getSymbol() == Obstacles.WALL.getSymbol() ||     //Sem saida para esta direcao
                down.getSymbol() == Obstacles.WALL.getSymbol())
                return true;                                        //True para mudar a direcao

            if(right.getSymbol() != Obstacles.WALL.getSymbol() ||   //há um cruzamento na direita ou na esquerda
                left.getSymbol() != Obstacles.WALL.getSymbol())
                return true;                                        //True para mudar de direcao
        }else if(direction == RIGHT || direction == LEFT){

            if(right.getSymbol() == Obstacles.WALL.getSymbol() ||               //Sem saida e mudar de direcao
                    left.getSymbol() == Obstacles.WALL.getSymbol()){
                return true;
            }
            if(top.getSymbol() != Obstacles.WALL.getSymbol()  ||
                    down.getSymbol() != Obstacles.WALL.getSymbol()){          //Há caminho noutras direcçoes entao mudar de d
                return true;
            }
        }
        return false;
    }


    private ArrayList<Integer> verifyGhostCave(Maze maze){

        ArrayList<Integer> possibleDirections = new ArrayList<>();
        IMazeElement currentElement = maze.get(getPosY(), getPosX());
        Portal portal = game.getPortal();

        if(currentElement == null)
            return null;

        if(currentElement.getSymbol() == Obstacles.GHOST_CAVE.getSymbol()){
            if(portal.getPosX() < getPosX())
                possibleDirections.add(LEFT);
            else if(portal.getPosY() > getPosX())
                possibleDirections.add(RIGHT);
            else if(portal.getPosY() < getPosY())
                possibleDirections.add(UP);
            else if(portal.getPosY() > getPosY())
                possibleDirections.add(DOWN);

            return possibleDirections;
        }

        if(currentElement.getSymbol() == Obstacles.PORTAL.getSymbol()){
            IMazeElement top = maze.get(getPosY() - 1 ,getPosX());
            IMazeElement left = maze.get(getPosY(), getPosX() - 1);
            IMazeElement right = maze.get(getPosY(), getPosX() + 1);
            IMazeElement down = maze.get(getPosY() + 1, getPosX());

            if(top == null || (top.getSymbol() != Obstacles.WALL.getSymbol() && top.getSymbol() != Obstacles.GHOST_CAVE.getSymbol())){
                possibleDirections.add(UP);
            }else if(down == null || (down.getSymbol() != Obstacles.WALL.getSymbol() && down.getSymbol() != Obstacles.GHOST_CAVE.getSymbol())){
                possibleDirections.add(DOWN);
            }else if(right == null || (right.getSymbol() != Obstacles.WALL.getSymbol() && right.getSymbol() != Obstacles.GHOST_CAVE.getSymbol())){
                possibleDirections.add(RIGHT);
            }else if(left == null || (left.getSymbol() != Obstacles.WALL.getSymbol() && left.getSymbol() != Obstacles.GHOST_CAVE.getSymbol())){
                possibleDirections.add(LEFT);
            }
            return possibleDirections;
        }

        return null;
    }


    private ArrayList<Integer> getValidDirections(Maze maze) {
        ArrayList<Integer> possibleDirections = new ArrayList<>();

        IMazeElement top = maze.get(getPosY() - 1 ,getPosX());
        IMazeElement left = maze.get(getPosY(), getPosX() - 1);
        IMazeElement right = maze.get(getPosY(), getPosX() + 1);
        IMazeElement down = maze.get(getPosY() + 1, getPosX());

        possibleDirections = verifyGhostCave(maze);
        if(possibleDirections != null){
            return possibleDirections;          //Retorna aqui se estiver dentro da ghost cave e faz estas açoes
        }
        possibleDirections = new ArrayList<>();


        // verifica se pode ir para cima
        if (top == null || (top.getSymbol() != Obstacles.WALL.getSymbol() && top.getSymbol() != Obstacles.PORTAL.getSymbol())) {
            possibleDirections.add(UP);
        }
        // verifica se pode ir para a direita
        if (right == null || (right.getSymbol() != Obstacles.WALL.getSymbol() && right.getSymbol() != Obstacles.PORTAL.getSymbol())) {
            possibleDirections.add(RIGHT);
        }
        // verifica se pode ir para a esquerda
        if (left == null || (left.getSymbol() != Obstacles.WALL.getSymbol() && left.getSymbol() != Obstacles.PORTAL.getSymbol())) {
            possibleDirections.add(LEFT);
        }
        if(down == null || (down.getSymbol() != Obstacles.WALL.getSymbol() && down.getSymbol() != Obstacles.PORTAL.getSymbol())){
            possibleDirections.add(DOWN);
        }

        if(possibleDirections.size() == 0)
            return null;

        // retorna um novo array com as direções possíveis
        return possibleDirections;
    }


    @Override
    public char getSymbol() {
        return 'B';
    }

}
