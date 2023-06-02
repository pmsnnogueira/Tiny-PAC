package pt.isec.pa.tinypac.utils;

import pt.isec.pa.tinypac.model.data.obstacles.Ball;
import pt.isec.pa.tinypac.model.data.obstacles.Fruit;
import pt.isec.pa.tinypac.model.data.obstacles.Power;



public enum Obstacles {

    WALL('x'),WARP('W'),BALL('o', Ball.BALL_POINTS),FRUIT('F', Fruit.FRUIT_POINTS),PACMAN_INITIAL_POSITION('M'),
    PACMAN('*'), BLINKY('B'), PINKY('P'), CLYDE('C'), INKY('I'),
    POWER('O',Power.POWER_POINTS),PORTAL('Y'),GHOST_CAVE('y');




    private final char symbol;
    private final int points;
    Obstacles(char c){
        this.symbol = c;
        this.points = 0;
    }
    Obstacles(char c, Integer i){
        this.symbol = c;
        this.points = i;
    }

    public int getPoints() {
        return points;
    }

    public char getSymbol() {
        return symbol;
    }
}
