package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.obstacles.Portal;
import pt.isec.pa.tinypac.utils.Obstacles;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

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
        this.minDistance = game.getMazeColumns() * DISTANCE_MIN_CORNER;
    }

    @Override
    public char getSymbol() {
        return 'P';
    }


    private boolean verifyMinimumDistance(){

        int cornerX = 0;
        int cornerY = 0;

        switch (cornerDirection){
            case TOP_RIGHT : {
                cornerX = (int)(game.getMazeColumns() - (game.getMazeColumns() * DISTANCE_MIN_CORNER));
                cornerY = (int)(game.getMazeRows() * DISTANCE_MIN_CORNER);
                break;
            }
            case BOTTOM_RIGHT:{
                cornerX = (int) (game.getMazeColumns() - (game.getMazeColumns() * DISTANCE_MIN_CORNER));
                cornerY = (int) (game.getMazeRows() - (game.getMazeRows() * DISTANCE_MIN_CORNER));;
                break;
            }
            case TOP_LEFT: {
                cornerX = (int)(game.getMazeColumns() * DISTANCE_MIN_CORNER);
                cornerY = (int)(game.getMazeRows() * DISTANCE_MIN_CORNER);
                break;
            }
            case BOTTOM_LEFT:{
                cornerX = (int)(game.getMazeColumns() * DISTANCE_MIN_CORNER);
                cornerY = (int)(game.getMazeRows() - (game.getMazeRows() * DISTANCE_MIN_CORNER));
                break;
            }
        }

        int result = (int)sqrt(((getPosX() - 29) * (getPosX() - 29)) + ((getPosY() - 1) * (getPosY() - 1)));
/*        System.out.println("Distancia do canto "+ result);
        System.out.println("DistanciaX "+ cornerX);
        System.out.println("DistanciaY "+ cornerY);*/

        //System.out.println("Pitagoras: " + result);

        if(cornerDirection == TOP_RIGHT) {
            //Está um problema com o if em baixo, ele faz o topRight bem, mas depois quando vai para o BottomLeft
            //ele verifica que a posicao atual é menor que a posicao do canto do BOTTOMLEFT, esta condicao vai ficar bue vezes true
            if (getPosX() <= cornerX && getPosY() <= cornerY) {
                /*System.out.println("\n\nDistancia minima atingida ");
                System.out.println("Pos: " + getPosX() + " Y:" + getPosY());
                System.out.println("CornerX: " + cornerX + " Y:" + cornerY);*/
                return true;
            }
        }else if(cornerDirection == BOTTOM_RIGHT) {
            if (getPosX() >= cornerX && getPosY() >= cornerY) {
                /*System.out.println("\n\nDistancia minima atingida ");
                System.out.println("Pos: " + getPosX() + " Y:" + getPosY());
                System.out.println("CornerX: " + cornerX + " Y:" + cornerY);*/
                return true;
            }
        }else if(cornerDirection == TOP_LEFT) {
            if (getPosX() <= cornerX && getPosY() <= cornerY) {
                /*System.out.println("\n\nDistancia minima atingida ");
                System.out.println("Pos: " + getPosX() + " Y:" + getPosY());
                System.out.println("CornerX: " + cornerX + " Y:" + cornerY);*/
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean evolve() {

        Maze maze = game.getMaze();
        /*int nextX = getPosX();
        int nextY = getPosY();

        if(verifyMinimumDistance()){
            //Change Direction
            //System.out.println("AQui2");
            switch (direction) {
                case TOP: {
                    nextY++;
                    direction = BOTTOM;
                    break;
                }
                case BOTTOM : {
                    nextY--;
                    direction = TOP;
                    break;
                }
                case LEFT : {
                    nextX++;
                    direction = RIGHT;
                    break;
                }
                case RIGHT : {
                    nextX--;
                    direction = LEFT;
                    break;
                }
            }

            setPos(nextX , nextY);
            changeDirection();
            return true;
        }




       // System.out.println("Initial: " + nextX + " " + nextY);



        if(cruzamento(maze,direction)) {
            ArrayList<Integer> possibleDirections = new ArrayList(getValidDirections(maze));
            for (int i = 0; i < possibleDirections.size(); i++) {
                switch (possibleDirections.get(i)) {
                    case TOP -> System.out.println("Top");
                   case BOTTOM -> System.out.println("Bottom");
                    case LEFT -> System.out.println("Left");
                    case RIGHT -> System.out.println("Right");
                }
            }
            //System.out.println(possibleDirections.size());
            if (possibleDirections.size() == 0) {
                System.out.println("Zero movimentos");
                if (direction == TOP)
                    direction = BOTTOM;
                else if (direction == BOTTOM)
                    direction = TOP;
                else if (direction == RIGHT)
                    direction = LEFT;
                else if (direction == LEFT)
                    direction = RIGHT;

            } else {
                direction = possibleDirections.get(new Random().nextInt(possibleDirections.size()));
                switch (direction) {
                    case TOP -> System.out.println("New Direction: Top");
                    case BOTTOM -> System.out.println("New Direction: Bottom");
                    case LEFT -> System.out.println("New Direction: Left");
                    case RIGHT -> System.out.println("New Direction: RIGHT");
                }
            }
        }
            // return false;
        //}


        switch (direction) {
            case TOP -> nextY--;
            case BOTTOM -> nextY++;
            case LEFT -> nextX--;
            case RIGHT -> nextX++;
        }

        //System.out.println("\n\tNew Pos: " + nextX + " Y: " + nextY);
        setPos(nextX , nextY);*/



        return true;
    }

    private void changeDirection() {
        //System.out.println("AQUi\n\n");
        if(cornerDirection == TOP_RIGHT){
            cornerDirection = BOTTOM_RIGHT;
            return;
        }else if(cornerDirection == BOTTOM_RIGHT){
            cornerDirection = TOP_LEFT;
            return;
        }else if(cornerDirection == TOP_LEFT){
            cornerDirection = BOTTOM_LEFT;
            return;
        }else if(cornerDirection == BOTTOM_LEFT){
            cornerDirection = TOP_RIGHT;
            return;
        }
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



    private ArrayList<Integer> getValidDirections(Maze maze){

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

        if(cornerDirection == TOP_RIGHT){
            if(top.getSymbol() != Obstacles.WALL.getSymbol() && direction != BOTTOM){
                possibleDirections.add(TOP);
                possibleDirections.add(TOP);
            }
            if(right.getSymbol() != Obstacles.WALL.getSymbol() && direction != LEFT){
                //GoRight
                possibleDirections.add(RIGHT);
                possibleDirections.add(RIGHT);
            }
            if(left.getSymbol() != Obstacles.WALL.getSymbol() && direction != RIGHT){
                possibleDirections.add(LEFT);
            }
            if(bottom.getSymbol() != Obstacles.WALL.getSymbol() && direction != TOP){
                possibleDirections.add(BOTTOM);
            }
            return possibleDirections;
        }

        if(cornerDirection == BOTTOM_RIGHT){
            if(bottom.getSymbol() != Obstacles.WALL.getSymbol() && direction != TOP){
                possibleDirections.add(BOTTOM);
                possibleDirections.add(BOTTOM);
            }
            if(right.getSymbol() != Obstacles.WALL.getSymbol() && direction != LEFT){
                //GoRight
                possibleDirections.add(RIGHT);
                possibleDirections.add(RIGHT);
            }
            if(left.getSymbol() != Obstacles.WALL.getSymbol() && direction != RIGHT){
                possibleDirections.add(LEFT);
            }
            if(top.getSymbol() != Obstacles.WALL.getSymbol() && direction != BOTTOM){
                possibleDirections.add(TOP);
            }
            return possibleDirections;
        }

        if(cornerDirection == TOP_LEFT){
            if(top.getSymbol() != Obstacles.WALL.getSymbol() && direction != BOTTOM){
                possibleDirections.add(TOP);
                possibleDirections.add(TOP);
            }
            if(left.getSymbol() != Obstacles.WALL.getSymbol() && direction != RIGHT){
                possibleDirections.add(LEFT);
                possibleDirections.add(LEFT);
            }
            if(right.getSymbol() != Obstacles.WALL.getSymbol() && direction != LEFT){
                //GoRight
                possibleDirections.add(RIGHT);
            }
            if(bottom.getSymbol() != Obstacles.WALL.getSymbol() && direction != TOP){
                possibleDirections.add(BOTTOM);
            }
            return possibleDirections;
        }

        if(cornerDirection == BOTTOM_LEFT){
            if(bottom.getSymbol() != Obstacles.WALL.getSymbol()){
                possibleDirections.add(BOTTOM);
                possibleDirections.add(BOTTOM);
            }
            if(left.getSymbol() != Obstacles.WALL.getSymbol()){
                possibleDirections.add(LEFT);
                possibleDirections.add(LEFT);
            }
            if(right.getSymbol() != Obstacles.WALL.getSymbol()){
                //GoRight
                possibleDirections.add(RIGHT);
            }
            if(top.getSymbol() != Obstacles.WALL.getSymbol()){
                possibleDirections.add(TOP);
            }
            return possibleDirections;
        }

        return null;
    }


    /*private ArrayList<Integer> getValidDirections(Maze maze , Integer direction) {
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
    }*/


}
