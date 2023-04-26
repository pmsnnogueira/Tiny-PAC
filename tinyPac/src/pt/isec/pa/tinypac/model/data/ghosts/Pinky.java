package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.obstacles.Portal;
import utils.Obstacles;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.sqrt;

public class Pinky extends Ghost {

    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    private static final int BOTTOM = 4;

    private static final int TOP_RIGHT = 5;
    private static final int BOTTOM_RIGHT = 6;
    private static final int TOP_LEFT = 7;
    private static final int BOTTOM_LEFT = 8;

    private static final double DISTANCE_MIN_CORNER = 0.15;


    private int direction;
    private int cornerDirection;

    private double minDistance;


    public Pinky(Game game, int posX, int posY){
        super(game,posX, posY);
        this.direction = TOP;
        this.cornerDirection = TOP_RIGHT;
        this.minDistance = 0;
    }

    @Override
    public char getSymbol() {
        return 'P';
    }


    private boolean changeCornerDirection(){

        int cornerX = 0;
        int cornerY = 0;

        switch (cornerDirection){
            case TOP_RIGHT : {
                cornerX = game.getMazeColumns();
                cornerY = 0;
                minDistance = DISTANCE_MIN_CORNER *
                break;
            }
            case TOP_LEFT: {
                cornerX = 0;
                cornerY = 0;
                break;
            }
            case BOTTOM_RIGHT:{
                cornerX = game.getMazeColumns();
                cornerY = game.getMazeRows();
                break;
            }
            case BOTTOM_LEFT:{
                cornerX = 0;
                cornerY = game.getMazeRows();
                break;
            }
        }

        double result = sqrt(cornerX * getPosX() + cornerY * getPosY());
        System.out.println("Distancia do canto "+ result);
        if(result <= DISTANCE_MIN_CORNER){
            System.out.println("\n\nDistancia minima atingida "+ result);
            return true;
        }
        return false;
    }



    @Override
    public boolean evolve() {

        changeCornerDirection();

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



        return true;
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


        IMazeElement top = maze.get(getPosY() - 1,getPosX());
        IMazeElement right = maze.get(getPosY() ,getPosX() + 1);
        IMazeElement left = maze.get(getPosY(),getPosX()-1);
        IMazeElement bottom = maze.get(getPosY() + 1,getPosX());
        IMazeElement actualPosition = maze.get(getPosY() , getPosX());
        Portal portal = game.getPortal();

        if(actualPosition.getSymbol() == Obstacles.GHOST_CAVE.getSymbol()){
            if(portal.getPosX() < getPosX()){
                //GoLeft
                possibleDirections.add(LEFT);
            }else if(portal.getPosX() > getPosX()){
                //Go Right
                possibleDirections.add(RIGHT);
            }else if(portal.getPosX() == getPosX()){
                if(portal.getPosY() < getPosY()){
                    //goTop
                    possibleDirections.add(TOP);
                }else{
                    //GoBottom
                    possibleDirections.add(BOTTOM);
                }
            }
            return possibleDirections;
        }

        if(direction == TOP){
            // verifica se pode ir para cima
            if (top.getSymbol() != Obstacles.WALL.getSymbol() && top.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(TOP);
            }
            // verifica se pode ir para a direita
            if (right.getSymbol() != Obstacles.WALL.getSymbol()&& right.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(RIGHT);
            }
            // verifica se pode ir para a esquerda
            if (left.getSymbol() != Obstacles.WALL.getSymbol() && left.getSymbol() != Obstacles.PORTAL.getSymbol()){             //Testar este) {
                possibleDirections.add(LEFT);
            }
        }else if(direction == LEFT){
            // verifica se pode ir para a esquerda
            if (left.getSymbol() != Obstacles.WALL.getSymbol() && left.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(LEFT);
            }
            // verifica se pode ir para cima
            if (top.getSymbol() != Obstacles.WALL.getSymbol() && top.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(TOP);
            }
            // verifica se pode ir para baixo
            if (bottom.getSymbol() != Obstacles.WALL.getSymbol() && bottom.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(BOTTOM);
            }
        }
        else if(direction == RIGHT){
            // verifica se pode ir para a direita
            if (right.getSymbol() != Obstacles.WALL.getSymbol() && right.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(RIGHT);
            }
            // verifica se pode ir para cima
            if (top.getSymbol() != Obstacles.WALL.getSymbol() && top.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(TOP);
            }
            // verifica se pode ir para baixo
            if (bottom.getSymbol() != Obstacles.WALL.getSymbol() && bottom.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(BOTTOM);
            }
        }else if(direction == BOTTOM){
            // verifica se pode ir para a esquerda
            if (left.getSymbol() != Obstacles.WALL.getSymbol() && left.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(LEFT);
            }
            // verifica se pode ir para a direita
            if (right.getSymbol() != Obstacles.WALL.getSymbol() && right.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(RIGHT);
            }
            // verifica se pode ir para baixo
            if (bottom.getSymbol() != Obstacles.WALL.getSymbol() && bottom.getSymbol() != Obstacles.PORTAL.getSymbol()) {
                possibleDirections.add(BOTTOM);
            }
        }





        // retorna um novo array com as direções possíveis
        return possibleDirections;
    }


}
