package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.obstacles.Portal;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Clyde extends Ghost implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int NONE = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    private static final int DOWN = 4;
    private int direction;

    private int pacmanDirection;

    private Integer toPacmanDirection;

    public Clyde(Game game,int posX, int posY){
        super(game,posX, posY);
        this.direction = UP;

        toPacmanDirection = 0;
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

    @Override
    public boolean evolve() {
        Maze maze = game.getMaze();

        ArrayList<Integer> aux= new ArrayList<>();
        aux.add(LEFT);
        aux.add(UP);
        aux.add(DOWN);
        aux.add(RIGHT);

        toPacmanDirection = whereIsPacman(maze, aux);
        if(toPacmanDirection != NONE){
            move(maze,toPacmanDirection);
            return true;
        }

        if(cruzamento(maze, direction)){

            //É possivel mudar de direcao
            ArrayList<Integer> validDirections = new ArrayList<>(getValidDirections(maze));
            if(validDirections.size() >= 2){
                //Escolher qual a melhor para ele e a aleatoriedade
                this.direction = chooseDirection(validDirections,direction);

            }else if(validDirections.size() == 1){
                direction = validDirections.get(0);
            }
        }
        move(maze, direction);



        return true;
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

    private Integer whereIsPacman(Maze maze, ArrayList<Integer> validDirections){

        Position pacmanPostion = game.getPacmanPosition();

        if(validDirections.contains(RIGHT)){
            if(pacmanPostion.getPosY() == getPosY() && pacmanPostion.getPosX() > getPosX()){
                //System.out.println("Vou ver na direita");
                for(int i = getPosX(); i <= pacmanPostion.getPosX(); i++){
                    IMazeElement element = maze.get(getPosY(), i);
                    System.out.println("right Pacman X " + pacmanPostion.getPosX() + " GhostX " + getPosX());
                    if(element != null &&
                            element.getSymbol() == Obstacles.WALL.getSymbol()){
                        //toPacmanDirection = NONE;
                        break;
                    }

                    if(i == pacmanPostion.getPosX()) {
                        System.out.println("Pacman Esta para a direita");
                        return RIGHT;
                    }
                }
            }
        }
        if(validDirections.contains(LEFT)){
            if(pacmanPostion.getPosY() == getPosY() && pacmanPostion.getPosX() < getPosX()){
                //System.out.println("Vou ver na esquerda");
                for(int i = getPosX(); i >= pacmanPostion.getPosX(); i--){
                    IMazeElement element = maze.get(getPosY(), i);
                    System.out.println("left Pacman X " + pacmanPostion.getPosX() + " GhostX " + getPosX());
                    if(element != null &&
                            element.getSymbol() == Obstacles.WALL.getSymbol()){
                       // toPacmanDirection = NONE;
                        break;
                    }
                    if(i == pacmanPostion.getPosX()) {
                        System.out.println("Pacman Esta para a Esquerda");
                        return LEFT;
                    }
                }
            }
        }
        if(validDirections.contains(UP)){
            if(pacmanPostion.getPosY() < getPosY() && pacmanPostion.getPosX() == getPosX()){
                //System.out.println("Up Pacman Y " + pacmanPostion.getPosY() + " GhostY " + getPosY());
                for(int i = getPosY(); i >= pacmanPostion.getPosY(); i--){
                    IMazeElement element = maze.get(getPosY(), i);
                    if(element != null &&
                            element.getSymbol() == Obstacles.WALL.getSymbol()){
                        //toPacmanDirection = NONE;
                        System.out.println("Vou Sair i = " + i);
                        break;
                    }

                    if(i == pacmanPostion.getPosY()) {
                        System.out.println("Pacman Esta para a Cima");
                        return UP;
                    }
                }
            }
        }

        if(validDirections.contains(DOWN)){
            if(pacmanPostion.getPosY() >  getPosY() && pacmanPostion.getPosX() == getPosX()){
                for(int i = getPosY(); i <= pacmanPostion.getPosY(); i++){
                    IMazeElement element = maze.get(getPosY(), i);

                    //System.out.println("Baixo Pacman Y " + pacmanPostion.getPosY() + " GhostY " + getPosY());
                    if(element != null &&
                            element.getSymbol() == Obstacles.WALL.getSymbol()){
                        //toPacmanDirection = NONE;
                        System.out.println("Vou Sair i = " + i);
                        break;
                    }

                    if(i == pacmanPostion.getPosY()) {
                        System.out.println("Pacman Esta para a DOWN");
                        return DOWN;
                    }
                }
            }
        }

        return NONE;
    }

    private int chooseDirection(ArrayList<Integer> validDirections, Integer direction){
        ArrayList<Integer> aux = new ArrayList<>(validDirections);

        aux.remove(oppositeDirection(direction));
        if(aux.size() == 0)
            return oppositeDirection(direction);
        int random = new Random().nextInt(aux.size());

        return aux.get(random);
    }

    private void addLastMove(Integer posX, Integer posY){
        pushLastPosition(posX,posY);
    }

    private boolean move(Maze maze, int direction){
        int nextPosX = getPosX();
        int nextPosY = getPosY();

        addLastMove(getPosX(), getPosY());

        switch (direction) {
            case UP -> nextPosY--;
            case DOWN -> nextPosY++;
            case LEFT -> nextPosX--;
            case RIGHT -> nextPosX++;
        }

        IMazeElement mazeElement = maze.get(nextPosY, nextPosX);
        if (mazeElement == null || mazeElement.getSymbol() != Obstacles.WALL.getSymbol()) {
            setPos(nextPosX, nextPosY);
            return true;
        }else{
            return false;
        }

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


    @Override
    public char getSymbol() {
        return Obstacles.CLYDE.getSymbol();
    }

}