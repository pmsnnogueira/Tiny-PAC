package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.obstacles.*;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.PacmanPosition;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
    private ArrayList<Warp> warps;
    private Integer foodRemaining;

    private static int currentTick = 1;
    private int tickAtBeginningOfFunction = 1;
    private int maxTick = 50;
    public Game(){
        this.level = 1;
        this.lives = 3;
        this.score = 0;
        this.maze = null;
        this.ghosts = new ArrayList<>();
        this.pacman = null;
        this.foodRemaining = 0;
        this.warps = new ArrayList<>();
    }

    public Game(Game game){
        this.level = game.getLevel();
        this.lives = game.getLives();
        this.score = game.getScore();
        this.maze = game.getMaze();
        this.ghosts = game.getGhosts();
        this.pacman = game.getPacman();
        this.foodRemaining = game.getFoodRemaining();
    }

    public void addWarps(Warp warp){
        this.warps.add(warp);
    }

    public ArrayList<Warp> getWarps(){
        return new ArrayList<>(warps);
    }
    public Integer getSizeWarps(){
        return warps.size();
    }
    public Warp getSpecificWarps(Integer posX, Integer posY){

        for(Warp aux : warps){
            if(aux.getPositionX() == posX && aux.getPositionY() == posY)
                return aux;
        }
        return null;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ArrayList<Ghost> getGhosts() {
        return new ArrayList<>(ghosts);
    }

    public Pacman getPacman() {
        return pacman;
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
        info += "\n\tlevel: " + level;

       return info;
    }


    public char[][] showMaze() {
        char[][] gameBoard;

        if(maze == null)
            return null;
        gameBoard = maze.getMaze();
        if(gameBoard == null)
            return null;

        for (int i = 0; i < mazeRows; i++) {
            for (int a = 0; a < mazeColumns; a++) {
                gameBoard[i][a] = getCharAtMazeElement(gameBoard,i,a);
            }
        }
        return gameBoard;
    }

    public char getCharAtMazeElement(Integer row, Integer column){

        if(maze == null)
            return ' ';
        char[][] gameBoard = maze.getMaze();
        if(gameBoard == null)
            return ' ';

        return getCharAtMazeElement(gameBoard, row,column);
    }

    private char getCharAtMazeElement(char[][] gameBoard, Integer row, Integer column){

        if(row == pacman.getPosY() && column == pacman.getPosX())
            return Obstacles.PACMAN.getSymbol();            //Return Pacman Position

        for (Ghost aux : ghosts)
            if (aux.getPosY() == row && aux.getPosX() == column)
                return aux.getSymbol();

        if (gameBoard[row][column] == Obstacles.GHOST_CAVE.getSymbol()
                || gameBoard[row][column] == Obstacles.PACMAN_INITIAL_POSITION.getSymbol())
            return gameBoard[row][column] = ' ';

        if(gameBoard[row][column] == Obstacles.WALL.getSymbol())
            return Obstacles.WALL.getSymbol();

        if(gameBoard[row][column] == Obstacles.FRUIT.getSymbol())
            return Obstacles.FRUIT.getSymbol();

        if(gameBoard[row][column] == Obstacles.BALL.getSymbol())
            return Obstacles.BALL.getSymbol();

        if(gameBoard[row][column] == Obstacles.PORTAL.getSymbol())
            return Obstacles.PORTAL.getSymbol();

        if(gameBoard[row][column] == Obstacles.POWER.getSymbol())
            return Obstacles.POWER.getSymbol();

        if(gameBoard[row][column] == Obstacles.WARP.getSymbol())
            return Obstacles.WARP.getSymbol();

        return ' ';
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

        tickAtBeginningOfFunction = 1;
        maxTick = 50;
        int counterPacman = 0;

        if(ghosts == null || pacman == null)
            return false;

        /*while (tickAtBeginningOfFunction <= maxTick){

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(currentTick % pacman.getTicksToMove() == 0){
                evolvePacman();
                eatFood();
                counterPacman++;
            }
            evolveGhosts();
            tickAtBeginningOfFunction++;
            currentTick++;
        }*/
        evolvePacman();
        eatFood();

        evolveGhosts();

        return true;
    }

    public void evolveGhosts(){
        for(Ghost ghost : ghosts){
            //if(currentTick % ghost.getTicksToMove() == 0) {
                if (!ghost.getLocked() && !ghost.getVulnerable()) {
                    ghost.evolve();
                } else if (ghost.getVulnerable()) {
                    ghost.returnToBase();
                }
            //}
        }
    }

    public void evolvePacman(){
        pacman.evolve();
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

        System.out.println("Level: " + level);
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
        return getLives() > 0;
    }

    public char getCharElementInPosition(int row, int column) {
        return getCharAtMazeElement(row,column);
    }

    public Direction getDirection() {
        return pacman.getDirection();
    }

    public boolean charIsGhosts(char c) {
        return c == Obstacles.BLINKY.getSymbol() ||
                c == Obstacles.PINKY.getSymbol() ||
                c == Obstacles.CLYDE.getSymbol() ||
                c == Obstacles.INKY.getSymbol();
    }

    public Position getRandomWarpPosition(Position position) {

        ArrayList<Warp> aux = new ArrayList<>(warps);

        for(Warp warp : warps){
            if(warp.getPosition().equals(position)){
                aux.remove(warp);
                break;
            }
        }
        int index = new Random().nextInt(aux.size());
        return aux.get(index).getPosition();
    }
}
