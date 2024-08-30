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

/**
 * The Inky class represents the Inky ghost in the game.
 * It extends the Ghost class and is Serializable.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Inky extends Ghost implements Serializable {
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


    /**
     * Constructs a Inky object with the specified game and position.
     *
     * @param game  The game instance.
     * @param posX  The X-coordinate of the initial position.
     * @param posY  The Y-coordinate of the initial position.
     */
    public Inky(Game game, int posX, int posY){
        super(game,posX, posY);
        this.direction = UP;
        this.cornerDirection = DOWN_RIGHT;
        this.minDistance = (int) (game.getMazeColumns() * DISTANCE_MIN_CORNER);
    }

    /**
     * Returns the symbol representing Inky.
     * @return The symbol representing Inky.
     */
    @Override
    public char getSymbol() {
        return Obstacles.INKY.getSymbol();
    }

    /**
     * Prints the valid directions.
     * @param validDirections The list of valid directions.
     */
    private void printValidPositions(ArrayList<Integer> validDirections){
        System.out.println("Valid Directions:");
        for(Integer a: validDirections){
            System.out.println(printDirection(a));
        }
    }

    /**
     * Prints the direction based on its integer value.
     * @param direction The direction integer value.
     * @return The string representation of the direction.
     */
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


    /**
     * Calculates the Euclidean distance between an object's position and a corner's position.
     * @param objX The x-coordinate of the object's position.
     * @param objY The y-coordinate of the object's position.
     * @param cornerX The x-coordinate of the corner's position.
     * @param cornerY The y-coordinate of the corner's position.
     * @return The Euclidean distance between the object's position and the corner's position.
     */
    public static double distToCorner(int objX, int objY, int cornerX, int cornerY) {
        int dx = objX - cornerX;
        int dy = objY - cornerY;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Verifies if the minimum distance to a corner is satisfied.
     * @param cornerDirection The direction of the corner.
     * @return True if the minimum distance to the corner is satisfied, false otherwise.
     */
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

    /**
     * Evolves the ghost.
     * @return Always returns `true`.
     */
    @Override
    public boolean evolve() {

        Maze maze = game.getMaze();

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

        return true;
    }

    /**
     * Returns the ghost to its base position.
     */
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

    /**
     * Changes the corner direction to the next corner direction in a clockwise order.
     * @return The next corner direction.
     */
    private int changeCornerDirection(Integer cornerDirection){
        if(cornerDirection == DOWN_RIGHT)
            return DOWN_LEFT;
        if(cornerDirection == DOWN_LEFT)
            return UP_RIGHT;
        if(cornerDirection == UP_RIGHT)
            return UP_LEFT;
        if(cornerDirection == UP_LEFT)
            return DOWN_RIGHT;

        return -1;
    }

    /**
     * Returns the opposite direction of the given direction.
     * @param direction The current direction.
     * @return The opposite direction of the given direction.
     */
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

    /**
     * Chooses a new direction for the ghost based on the valid directions and the current direction.
     * @param validDirections The list of valid directions.
     * @param direction The current direction.
     * @return The new direction chosen for the ghost.
     */
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

    /**
     * Adds the last move to the ghost's position history.
     * @param posX The x-coordinate of the ghost's position.
     * @param posY The y-coordinate of the ghost's position.
     */
    private void addLastMove(Integer posX, Integer posY){
        pushLastPosition(posX,posY);
    }

    /**
     * Moves the ghost in the specified direction.
     * @param maze The maze in which the ghost is moving.
     * @param direction The direction in which the ghost is moving.
     * @return True if the ghost successfully moves to the next position, false otherwise.
     */
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

    /**
     * Checks if the ghost is at a crossroad in the maze.
     *
     * @param maze      The maze instance.
     * @param direction The current direction of the ghost.
     * @return `true` if the ghost is at a crossroad, `false` otherwise.
     */
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


    /**
     * Checks if the ghost is in the ghost cave or near the portal and determines the possible directions to move.
     *
     * @param maze The maze instance.
     * @return A list of possible directions to move.
     */
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

    /**
     * Retrieves the valid directions that the ghost can take in the maze.
     *
     * @param maze The maze instance.
     * @return A list of valid directions.
     */
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
