package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.obstacles.Portal;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Pinky extends Ghost implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    private static final int DOWN = 4;

    private static final int UP_RIGHT = 5;
    private static final int DOWN_RIGHT = 6;
    private static final int UP_LEFT = 7;
    private static final int DOWN_LEFT = 8;

    private static final double DISTANCE_MIN_CORNER = 0.15;


    private int direction;
    private int cornerDirection;

    private int minDistance;


    public Pinky(Game game, int posX, int posY){
        super(game,posX, posY);
        this.direction = UP;
        this.cornerDirection = UP_RIGHT;
        this.minDistance = (int) (game.getMazeColumns() * DISTANCE_MIN_CORNER);
    }

    @Override
    public char getSymbol() {
        return Obstacles.PINKY.getSymbol();
    }

    private void printValidPositions(ArrayList<Integer> validDirections){
        System.out.println("Valid Directions:");
        for(Integer a: validDirections){
            System.out.println(printDirection(a));
        }
    }

    private String printDirection(Integer direction){
        String string = new String();
        switch (direction){
            case UP -> string = ("\tUP");
            case DOWN -> string = ("\tDOWN");
            case LEFT -> string = ("\tLEFT");
            case RIGHT -> string = ("\tRIGHT");
        }
        return string;
    }

    public static double distToCorner(int objX, int objY, int cornerX, int cornerY) {
        int dx = objX - cornerX;
        int dy = objY - cornerY;
        return Math.sqrt(dx*dx + dy*dy);
    }

    private boolean verifyMinimumDistance(Integer cornerDirection){
        double distToCorner = 0;
        switch (cornerDirection){
            case UP_RIGHT -> {
                distToCorner = distToCorner(getPosX(), getPosY(), game.getMazeColumns() - 2, 1);
            }
            case DOWN_RIGHT -> {
                distToCorner = distToCorner(getPosX(), getPosY(), game.getMazeColumns() - 2, game.getMazeRows() - 2);
            }
            case UP_LEFT -> {
                distToCorner = distToCorner(getPosX(), getPosY(), 1, 1);
            }
            case DOWN_LEFT -> {
                distToCorner = distToCorner(getPosX(), getPosY(), 1, game.getMazeRows() - 2);
            }
        }

        if (distToCorner / Math.min(game.getMazeColumns(), game.getMazeRows()) <= DISTANCE_MIN_CORNER) {            //O Fantasma está a uma distancia minima do canto

            System.out.println("Fantasma perto de um canto!...\n\n");
            return true;
        }
        return false;
    }

    @Override
    public boolean evolve() {

       /* Maze maze = game.getMaze();

        if(verifyMinimumDistance(cornerDirection)){
            //Mudar a direcao do canto
            this.cornerDirection = changeCornerDirection(cornerDirection);
        }

        if(cruzamento(maze, direction)){
            //Mudar de direcao
            ArrayList<Integer> validDirections = new ArrayList<>(getValidDirections(maze));
            //printValidPositions(validDirections);
            if(validDirections.size() >= 2){

                //Escolher qual a melhor para ele e a aleatoriedade
                this.direction = chooseDirection(validDirections,direction);

                //System.out.p  rintln(printDirection(direction));
            }else if(validDirections.size() == 1){
                direction = validDirections.get(0);
            }
        }

        move(maze, direction);
*/
        return true;
    }

    @Override
    public void returnToBase(){

        if(getVulnerable()){
            if(!isMovementsEmpty()){
                Position lastPositon = popLastPosition();
                setPos(lastPositon.getPosX(), lastPositon.getPosY());
                return;
            }
            //unlockGhost();
            reset();
            setTicksToMove(DEFAULT_TICKS_TO_MOVE_GHOST);
        }

        return;
    }

    private int changeCornerDirection(Integer cornerDirection){
        if(cornerDirection == UP_RIGHT)
            return DOWN_RIGHT;
        if(cornerDirection == DOWN_RIGHT)
            return UP_LEFT;
        if(cornerDirection == UP_LEFT)
            return DOWN_LEFT;
        if(cornerDirection == DOWN_LEFT)
            return UP_RIGHT;

        return -1;
    }

    private Integer oppositeDirection(Integer direction){
        if(direction == UP)
            return DOWN;
        if(direction == LEFT)
            return RIGHT;
        if(direction == RIGHT)
            return LEFT;
        if(direction == DOWN)
            return UP;

        return -1;
    }

    private int chooseDirection(ArrayList<Integer> validDirections, Integer direction){

        ArrayList<Integer> aux = new ArrayList<>();

        switch (cornerDirection){
            case UP_RIGHT -> {
                if(validDirections.contains(UP))
                    aux.add(UP);
                if(validDirections.contains(RIGHT))
                    aux.add(RIGHT);

                if(aux.size() == 0){
                    //Escolher entre esquerda e baixo
                    if(validDirections.contains(LEFT))
                        aux.add(LEFT);
                    if(validDirections.contains(DOWN))
                        aux.add(DOWN);
                    return aux.get(new Random().nextInt(aux.size()));
                }
            }
            case DOWN_RIGHT -> {
                if(validDirections.contains(RIGHT))
                    aux.add(RIGHT);
                if(validDirections.contains(DOWN))
                    aux.add(DOWN);

                if(aux.size() == 0) {
                    //Escolher entre esquerda e UP
                    if (validDirections.contains(LEFT))
                        aux.add(LEFT);
                    if (validDirections.contains(UP))
                        aux.add(UP);
                }
            }
            case UP_LEFT -> {
                if(validDirections.contains(UP))
                    aux.add(UP);
                if(validDirections.contains(LEFT))
                    aux.add(LEFT);
                if(aux.size() == 0) {
                    //Escolher entre esquerda e UP
                    if (validDirections.contains(RIGHT))
                        aux.add(RIGHT);
                    if (validDirections.contains(DOWN))
                        aux.add(DOWN);
                }

            }
            case DOWN_LEFT -> {
                if(validDirections.contains(DOWN))
                    aux.add(DOWN);
                if(validDirections.contains(LEFT))
                    aux.add(LEFT);

                if(aux.size() == 0){
                    //Escolher entre esquerda e UP
                    if(validDirections.contains(RIGHT))
                        aux.add(RIGHT);
                    if(validDirections.contains(UP))
                        aux.add(UP);
                }
            }
        }

        if(aux.size() > 1)
            return aux.get(new Random().nextInt(aux.size()));
        else if(aux.size() == 1)
            return aux.get(0);

        return 0;
    }

    private void addLastMove(Integer posX, Integer posY){
        pushLastPosition(posX,posY);
    }

    private boolean move(Maze maze, int direction){

        int nextPosX = getPosX();
        int nextPosY = getPosY();

        addLastMove(getPosX(),getPosY());

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

        IMazeElement up = maze.get(getPosY() - 1 ,getPosX());
        IMazeElement left = maze.get(getPosY(), getPosX() - 1);
        IMazeElement right = maze.get(getPosY(), getPosX() + 1);
        IMazeElement down = maze.get(getPosY() + 1, getPosX());

        //Verificar se é uma parede dos fantasmas
        if((up != null && up.getSymbol() == Obstacles.PORTAL.getSymbol()) ||
                (down != null && down.getSymbol() == Obstacles.PORTAL.getSymbol()) ||
                (right != null && right.getSymbol() == Obstacles.PORTAL.getSymbol())||
                (left != null && left.getSymbol() == Obstacles.PORTAL.getSymbol())
        )
            return true;

        if(direction == UP || direction == DOWN){
            if ((up != null && down != null) && (up.getSymbol() == Obstacles.WALL.getSymbol() ||     //Sem saida para esta direcao
                    down.getSymbol() == Obstacles.WALL.getSymbol()))
                return true;                                        //True para mudar a direcao

            if((right == null || left == null) || right.getSymbol() != Obstacles.WALL.getSymbol() ||   //há um cruzamento na direita ou na esquerda
                    left.getSymbol() != Obstacles.WALL.getSymbol())
                return true;                                        //True para mudar de direcao

            if((up == null && down != null && down.getSymbol() == Obstacles.WALL.getSymbol()) ||
                    (up != null && down == null && up.getSymbol() == Obstacles.WALL.getSymbol()))
                return true;                            //True para mudar de direcao


        }else if(direction == RIGHT || direction == LEFT){

            if ((right!= null && left != null) && (right.getSymbol() == Obstacles.WALL.getSymbol() ||               //Sem saida e mudar de direcao
                    left.getSymbol() == Obstacles.WALL.getSymbol())) {
                return true;
            }

            if ((up == null || down == null) || (up.getSymbol() != Obstacles.WALL.getSymbol() ||
                    down.getSymbol() != Obstacles.WALL.getSymbol())) {          //Há caminho noutras direcçoes entao mudar de d
                return true;
            }

            if((right == null && left != null && left.getSymbol() == Obstacles.WALL.getSymbol())
                    || (right != null && left == null && right.getSymbol() == Obstacles.WALL.getSymbol()))
                return true;

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
            if(portal.getPosX() > getPosX())
                possibleDirections.add(RIGHT);
            if(portal.getPosY() < getPosY())
                possibleDirections.add(UP);
            if(portal.getPosY() > getPosY())
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

        //printValidPositions(possibleDirections);
        return possibleDirections;
    }



}
