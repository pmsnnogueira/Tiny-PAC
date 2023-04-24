package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.ghosts.Pinky;
import pt.isec.pa.tinypac.model.data.obstacles.*;
import utils.Obstacles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private Integer level;
    private Integer lives;
    private Integer points;
    private Maze maze;
    private Integer mazeRows;
    private Integer mazeColumns;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts;

    public Game(){
        this.level = 1;
        this.lives = 3;
        this.points = 0;
        this.maze = null;
        this.ghosts = new ArrayList<>();
        this.pacman = null;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getMazeRows() {
        return mazeRows;
    }

    public Integer getMazeColumns() {
        return mazeColumns;
    }

    public void setMazeRows(Integer mazeRows) {
        this.mazeRows = mazeRows;
    }

    public void setMazeColumns(Integer mazeColumns) {
        this.mazeColumns = mazeColumns;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public void setGhosts(ArrayList<Ghost> ghosts) {
        this.ghosts = ghosts;
    }

    public Maze getMaze() {
        return maze;
    }

    public char[][] showMaze() {
        char[][] gameBoard;
        gameBoard = maze.getMaze();
        if(gameBoard == null)
            return null;

        for (int i = 0; i < mazeRows; i++) {
            for (int a = 0; a < mazeColumns; a++) {

                if(i == pacman.getPosY() && a == pacman.getPosX())
                    gameBoard[i][a] = pacman.getSymbol();

                for (Ghost aux : ghosts) {
                    if (aux.getPosY() == i && aux.getPosX() == a) {
                        gameBoard[i][a] = aux.getSymbol();
                    }
                }

                if (gameBoard[i][a] == Obstacles.GHOST_CAVE.getSymbol()
                        || gameBoard[i][a] == Obstacles.PACMAN_INITIAL_POSITION.getSymbol()) {
                    gameBoard[i][a] = ' ';
                    continue;
                }

               //System.out.print(gameBoard[i][a]);
            }
            //System.out.println();
        }
        return gameBoard;
    }

    public void setLevel(Integer level){
        this.level = level;
    }

    //Verificar se existem os ficheiros dos mapas
    boolean verifyMapLevelExistence(){
        return false;
    }

    //Verificar se já existe um jogo guardado
    boolean verifyPreviousSaveGame(){
        return false;
    }

    public boolean evolve() {

        List<GameObjects> gameObjects = new ArrayList<>(ghosts);
        gameObjects.add(pacman);

        for(var object : gameObjects)
            object.evolve();


        return true;
    }
}
