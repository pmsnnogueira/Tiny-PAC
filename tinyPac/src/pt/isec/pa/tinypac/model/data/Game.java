package pt.isec.pa.tinypac.model.data;


import pt.isec.pa.tinypac.model.data.obstacles.*;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.Position;


import java.util.ArrayList;
import java.util.List;

public class Game {


    private static final char LIVES_ICON = 'A';
    private Integer level;
    private Integer lives;
    private Integer score;
    private Maze maze;
    private Integer mazeRows;
    private Integer mazeColumns;
    private Pacman pacman;
    private Portal portal;
    private ArrayList<Ghost> ghosts;


    public Game(){
        this.level = 1;
        this.lives = 3;
        this.score = 0;
        this.maze = null;
        this.ghosts = new ArrayList<>();
        this.pacman = null;
    }

    public boolean changeDirection(Direction direction){
        return pacman.setDirection(direction);
    }

    public Portal getPortal() {
        return portal;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
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

    public String showGameInfo(){

        String info = "Score: " + score + "\tLives: ";
        for(int i = 0; i < lives ; i++){
            info += LIVES_ICON;
        }

       return info;
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
                }

            }
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

        if(ghosts == null || pacman == null)
            return false;

        pacman.evolve();
        eatFood();
        for(Ghost ghost : ghosts){
            if(!ghost.getLocked()){
                ghost.evolve();
            }
        }

        if(!controlGame()){
            //resetLevel();
        }


        return true;
    }


    public boolean controlGame(){

        for(Ghost ghost: ghosts){
            if(ghost.getPosX() == pacman.getPosX() && ghost.getPosY() == pacman.getPosY()){
                //Ghost in same place as pacman
                if(pacman.getPower()){
                    //resetLevel();
                    return false;
                }else{
                    pacmanEatGhost(ghost);
                }
            }
        }
        return true;
    }

    private void pacmanEatGhost(Ghost ghost){
        ghost.reset();
    }

    public boolean eatFood(){
        Position position = pacman.getCurrentPosition();
        IMazeElement element = maze.get(position.getPosY(), position.getPosX());
        if(element == null){
            return false;
        }

        if(element.getSymbol() == Obstacles.BALL.getSymbol() ||
                element.getSymbol() == Obstacles.FRUIT.getSymbol() ||
                element.getSymbol() == Obstacles.POWER.getSymbol()){
            incrementPoints(element);
            if(maze.set(position.getPosY(), position.getPosX(), null)){
                return true;
            }
            if(element.getSymbol() == Obstacles.POWER.getSymbol()){
                pacman.setPower(true);
            }
        }
        return false;
    }

    private void incrementPoints(IMazeElement element){
        if(element.getSymbol() == Obstacles.FRUIT.getSymbol()){
            this.score += Obstacles.FRUIT.getPoints();
            return;
        }
        if(element.getSymbol() == Obstacles.BALL.getSymbol()){
            this.score += Obstacles.BALL.getPoints();
        }
        if(element.getSymbol() == Obstacles.POWER.getSymbol()){
            this.score += Obstacles.POWER.getPoints();
        }
    }

    public boolean unlockGhosts() {

        if(ghosts == null)
            return false;

        for(Ghost a : ghosts)
            a.setLocked(false);

        return true;
    }
}
