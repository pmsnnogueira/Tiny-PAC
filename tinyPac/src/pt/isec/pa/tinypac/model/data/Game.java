package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.obstacles.*;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.PacmanPosition;
import java.util.ArrayList;

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
    private Integer foodRemaining;

    public Game(){
        this.level = 1;
        this.lives = 3;
        this.score = 0;
        this.maze = null;
        this.ghosts = new ArrayList<>();
        this.pacman = null;
        this.foodRemaining = 0;
    }

    public void incFoodRemaining(){
        this.foodRemaining++;
    }

    private void decFoodRemaining() {
        this.foodRemaining--;
    }

    public Integer getFoodRemaining() {
        return foodRemaining;
    }

    public Boolean isAnyFoodRemaining(){
        if(getFoodRemaining() > 0)
            return true;
        return false;
    }

    public void setFoodRemaining(Integer foodRemaining) {
        this.foodRemaining = foodRemaining;
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

        evolvePacman();
        eatFood();


        evolveGhosts();

        return true;
    }

    public void evolvePacman(){
        pacman.evolve();
    }

    public void evolveGhosts(){
        for(Ghost ghost : ghosts){
            if(!ghost.getLocked() && !ghost.getVulnerable()){
                ghost.evolve();
            } else if(ghost.getVulnerable()){
                ghost.returnToBase();
            }
        }
    }

    public Integer controlGame(){

        if(getFoodRemaining() == 0){    //Level Completed
            incLevel();
            return 2;
        }

        for(Ghost ghost: ghosts){
            if(ghost.getPosX() == pacman.getPosX() && ghost.getPosY() == pacman.getPosY()){
                //Ghost in same place as pacman
                if(pacman.getPower()){
                    pacmanEatGhost(ghost);
                    //resetLevel();
                }else{

                    return -1;          //Pacman MORREU
                }
            }
        }

        if(pacman.getPower()) {
            return 1;       //Pacman With powers
        }

        return 0;
    }

    private void incLevel() {
        level++;
    }

    private void pacmanEatGhost(Ghost ghost){


    }

    private void ghostEatPacman(){
        pacman.reset();
        decLives();
        resetGhosts();
    }

    private void decLives() {
        this.lives--;
    }

    public boolean eatFood(){
        PacmanPosition pacmanPosition = pacman.getCurrentPosition();
        IMazeElement element = maze.get(pacmanPosition.getPosY(), pacmanPosition.getPosX());
        if(element == null){
            return false;
        }

        if(element.getSymbol() == Obstacles.BALL.getSymbol() ||
                element.getSymbol() == Obstacles.FRUIT.getSymbol() ||
                element.getSymbol() == Obstacles.POWER.getSymbol())
        {
            incrementPoints(element);

            if(element.getSymbol() == Obstacles.POWER.getSymbol())
                pacman.setPower(true);

            maze.set(pacmanPosition.getPosY(), pacmanPosition.getPosX(), null);
            decFoodRemaining();
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

    public boolean changeLockGhosts(Boolean operation) {

        if(ghosts == null)
            return false;

        for(Ghost a : ghosts)
            a.setLocked(operation);

        return true;
    }

    public void ghostsVulnerable(){
        for(Ghost ghost: ghosts)
            ghostVulnerable(ghost);
    }

    public void ghostVulnerable(Ghost ghost){
        ghost.setVulnerable(true);
    }

    public int pacmanManager() {

        if(isAnyLiveRemaining()){
            //Pacman has lives to continue the level
            ghostEatPacman();
            System.out.println("Pacman died: lives  " + lives);
            if(!isAnyLiveRemaining())
                return -1;          //GameOver
            return 1;
        }
        //GameOver
        return -1;
    }

    private void resetGhosts(){
        for(Ghost ghost : ghosts){
            ghost.reset();
        }
    }
    private void resetPacman() {
        this.pacman.reset();
    }

    private void resetLevel() {
        resetGhosts();
        resetPacman();
    }

    public Integer getLives() {
        return lives;
    }
    public Boolean isAnyLiveRemaining(){
        if(getLives() > 0)
            return true;
        return false;
    }
}
