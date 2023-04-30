package utils;

public enum Obstacles {
    WALL('x'),WARP('W'),BALL('o'),FRUIT('F'),PACMAN_INITIAL_POSITION('M'),
    POWER('O'),PORTAL('Y'),GHOST_CAVE('y');

    private final char symbol;
    Obstacles(char c){
        this.symbol = c;
    }

    public char getSymbol() {
        return symbol;
    }
}
