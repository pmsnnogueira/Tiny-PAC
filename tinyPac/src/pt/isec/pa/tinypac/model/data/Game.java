package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.obstacles.*;
import pt.isec.pa.tinypac.ui.gui.resources.SoundManager;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.PacmanPosition;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Game class represents the game state and logic of a Tiny-PAC game.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
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

    private Integer eatBallsCounter;

    /**
     * Constructs a new instance of the `Game` class with default values.
     */
    public Game(){
        this.level = 2;
        this.lives = 1;
        this.score = 0;
        this.maze = null;
        this.ghosts = new ArrayList<>();
        this.pacman = null;
        this.foodRemaining = 0;
        this.warps = new ArrayList<>();
        this.eatBallsCounter = 0;
    }

    /**
     * Constructs a new instance of the Game class by copying another Game object.
     * @param game The `Game` object to copy.
     */
    public Game(Game game){
        this.level = game.getLevel();
        this.lives = game.getLives();
        this.score = game.getScore();
        this.maze = game.getMaze();
        this.mazeRows = game.getMazeRows();
        this.mazeColumns = game.getMazeColumns();
        this.portal = game.getPortal();
        this.ghosts = game.getGhosts();
        this.pacman = game.getPacman();
        this.foodRemaining = game.getFoodRemaining();
        this.warps = game.getWarps();
        this.eatBallsCounter = game.getEatBallsCounter();
    }

    /**
     * Retrieves the counter for the number of eaten balls.
     * @return The counter for the number of eaten balls.
     */
    public Integer getEatBallsCounter() {
        return eatBallsCounter;
    }

    public void addWarps(Warp warp){
        this.warps.add(warp);
    }

    /**
     * Retrieves the list of warp elements in the maze.
     * @return The list of warp elements.
     */
    public ArrayList<Warp> getWarps(){
        return new ArrayList<>(warps);
    }

    /**
     * Returns the number of warps in the game.
     * @return The number of warps.
     */
    public Integer getSizeWarps(){
        return warps.size();
    }

    /**
     * Retrieves a specific warp at the given position.
     * @param posX The X position of the warp.
     * @param posY The Y position of the warp.
     * @return The Warp object at the specified position, or null if no warp is found.
     */
    public Warp getSpecificWarps(Integer posX, Integer posY){

        for(Warp aux : warps){
            if(aux.getPositionX() == posX && aux.getPositionY() == posY)
                return aux;
        }
        return null;
    }

    /**
     * Retrieves the current score of the game.
     * @return The current score of the game.
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Sets the score of the game.
     * @param score The current score of the game.
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * Retrieves the list of ghost characters in the game.
     * @return The list of ghost characters.
     */
    public ArrayList<Ghost> getGhosts() {
        return new ArrayList<>(ghosts);
    }

    /**
     * Retrieves the player Pacman object.
     * @return The Pacman character.
     */
    public Pacman getPacman() {
        return pacman;
    }

    /**
     * Increase the number of remaining food items by 1.
     */
    public void incFoodRemaining(){
        this.foodRemaining++;
    }

    /**
     * Decreases the number of remaining food items by 1.
     */
    private void decFoodRemaining() {
        this.foodRemaining--;
    }

    /**
     * Retrieves the number of remaining food elements in the maze.
     * @return The number of remaining food elements.
     */
    public Integer getFoodRemaining() {
        return foodRemaining;
    }

    public Boolean isAnyFoodRemaining(){
        if(getFoodRemaining() > 0)
            return true;
        return false;
    }

    /**
     * Sets the number of remaining food elements in the maze.
     * @param foodRemaining The number of remaining food elements.
     */
    public void setFoodRemaining(Integer foodRemaining) {
        this.foodRemaining = foodRemaining;
    }

    public boolean changeDirection(Direction direction){
        return pacman.setDirection(direction);
    }

    /**
     * Retrieves the portal for teleportation in the maze.
     * @return The portal for teleportation.
     */
    public Portal getPortal() {
        return portal;
    }

    /**
     * Sets the portal for teleportation in the maze.
     * @param portal The portal for teleportation.
     */
    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    public Integer getLevel() {
        return level;
    }

    /**
     * Retrieves the number of rows in the maze.
     * @return The number of rows in the maze.
     */
    public Integer getMazeRows() {
        return mazeRows;
    }

    /**
     * Retrieves the number of columns in the maze.
     * @return The number of columns in the maze.
     */
    public Integer getMazeColumns() {
        return mazeColumns;
    }

    /**
     * Sets the number of rows in the maze.
     * @param mazeRows The number of rows in the maze.
     */
    public void setMazeRows(Integer mazeRows) {
        this.mazeRows = mazeRows;
    }

    /**
     * Sets the number of columns in the maze.
     * @param mazeColumns The number of columns in the maze.
     */
    public void setMazeColumns(Integer mazeColumns) {
        this.mazeColumns = mazeColumns;
    }

    /**
     * Sets the maze for the game.
     * @param maze The maze for the game.
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Sets the Pacman.
     * @param pacman The Pacman character.
     */
    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    /**
     * Sets the list of ghost characters in the game.
     * @param ghosts The list of ghost characters.
     */
    public void setGhosts(ArrayList<Ghost> ghosts) {
        this.ghosts = ghosts;
    }


    /**
     * Retrieves the maze for the game.
     * @return The maze for the game.
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Returns a string containing the current game information.
     * @return A string with the game information.
     */
    public String showGameInfo(){

        String info = "Score: " + score + "\tLives: " + lives;
        info += "\n\tlevel: " + level;

       return info;
    }


    /**
     * Returns the game maze as a bi dimensional character array.
     * @return The game maze as a bi dimensional character array, or null if the maze is not initialized.
     */
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

    /**
     * Returns the character at the specified position in the game maze.
     * @param row    The row index of the maze element.
     * @param column The column index of the maze element.
     * @return The character at the specified position, or ' ' (empty space) if no valid character is found.
     */
    public char getCharAtMazeElement(Integer row, Integer column){

        if(maze == null)
            return ' ';
        char[][] gameBoard = maze.getMaze();
        if(gameBoard == null)
            return ' ';

        return getCharAtMazeElement(gameBoard, row,column);
    }

    /**
     * Returns the character at the specified position in the game maze.
     * @param gameBoard The bi dimesnional character array representing the game maze.
     * @param row       The row index of the maze element.
     * @param column    The column index of the maze element.
     * @return The character at the specified position, or ' ' (empty space) if no valid character is found.
     */
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

    /**
     * Sets the level of the game.
     * @param level The level of the game.
     */
    public void setLevel(Integer level){
        this.level = level;
    }


    /**
     * Evolves the game state by moving the Pacman and ghosts.
     * @return true if the game state was updated, false otherwise.
     */
    public boolean evolve() {
        boolean pacmanRes = false, ghostRes = false;
        boolean update = false;

        if(ghosts == null || pacman == null)
            return false;

        if (tickAtBeginningOfFunction <= maxTick){
            if(currentTick % pacman.getTicksToMove() == 0){
                evolvePacman();
                eatFood();
                pacmanRes = true;
                update = true;
            }

            ghostRes = evolveGhosts();

            if(ghostRes || pacmanRes)
                update = true;
            tickAtBeginningOfFunction++;
            currentTick++;
        }

        if(pacmanRes && ghostRes){
            tickAtBeginningOfFunction = 1;
            currentTick = 1;
        }

        return update;
    }

    /**
     * Evolves the ghosts in the game state.
     * @return true if the ghosts were updated, false otherwise.
     */
    public boolean evolveGhosts(){
        boolean update = false;

        for(Ghost ghost : ghosts){
            if(currentTick % ghost.getTicksToMove() == 0) {
                if (!ghost.getLocked() && !ghost.getVulnerable()) {
                    ghost.evolve();

                } else if (ghost.getVulnerable()) {
                    ghost.returnToBase();
                }
                update = true;
            }
        }

        return update;
    }

    /**
     * Evolves the Pacman in the game state.
     */
    public void evolvePacman(){
        pacman.evolve();
    }

    /**
     * Controls the game state and returns the current game state code.
     * @return 2 if the level is completed, -1 if Pacman died, or 0 if the game is in progress.
     */
    public Integer controlGame(){

        if(getFoodRemaining() == 0){    //Level Completed
            incLevel();
            return 2;
        }

        for(Ghost ghost: ghosts){
            if(ghost.getPosX() == pacman.getPosX() && ghost.getPosY() == pacman.getPosY()){
                //Ghost in same place as pacman
                    return -1;
            }
        }

        if(pacman.getPower()) {
            return 1;       //Pacman With powers
        }

        return 0;
    }

    /**
     * Controls the game state and returns the current game state code.
     * @return 2 if the level is completed, -1 if Pacman died, or 0 if the game is in progress.
     */
    public Integer controlGameState(){

        if(getFoodRemaining() == 0){    //Level Completed
            incLevel();
            return 2;
        }

        for(Ghost ghost: ghosts){
            if(ghost.getPosX() == pacman.getPosX() && ghost.getPosY() == pacman.getPosY()){
                //Ghost in same place as pacman
                return -1;          //Pacman MORREU
            }
        }
        if(pacman.getPower()) {
            return 1;       //Pacman With powers
        }

        return 0;
    }

    /**
     * Controls the game state in the vulnerable state and returns the current game state code.
     * @return 2 if the level is completed, -1 if Pacman died, 1 if Pacman has powers, or 0 if the game is in progress.
     */
    public Integer controlGameVulnerableState(){
        if(getFoodRemaining() == 0){    //Level Completed
            incLevel();
            return 2;
        }

        for(Ghost ghost: ghosts){
            if(ghost.getPosX() == pacman.getPosX() && ghost.getPosY() == pacman.getPosY()){
                //Ghost in same place as pacman
                if(pacman.getPower() && ghost.getVulnerable() && !ghost.getDead()) {
                    System.out.println("Comi um Ghost");
                    pacmanEatGhost(ghost);
                }else {
                    //Pacman Morreu
                    if(!ghost.getVulnerable()) {
                        System.out.println("Pacman Morreu");
                        return -1;
                    }
                }
            }
            if(pacman.getPower() && ghost.isInInicialPosition()) {
                //System.out.println("Ola");
                ghost.changeToUnVulnerable();
                if(everyGhostsNotVulnerable()){
                    pacman.setPower(false);
                    return 1;       //MUDAR DE ESTADO PARA O GAME
                }
            }
        }

        return 0;
    }

    /**
     * Checks if all the ghosts in the game state are not vulnerable.
     * @return true if all the ghosts are not vulnerable, false otherwise.
     */
    private boolean everyGhostsNotVulnerable() {
        for(Ghost ghost : ghosts){
            if(ghost.getVulnerable())
                return false;
        }
        return true;
    }

    /**
     * Increments the level counter.
     */
    private void incLevel() {
        level++;
    }

    /**
     * Handles the event when Pacman eats a ghost.
     * @param ghost The ghost that Pacman eats.
     */
    private void pacmanEatGhost(Ghost ghost){

        ghost.setDead(true);
        ghost.setTicksToMove(3);

    }

    /**
     * Handles the event when a ghost eats Pacman.
     */
    private void ghostEatPacman(){
        decLives();
        resetLevel();
    }


    private void decLives() {
        this.lives--;
    }

    /**
     * Checks if Pacman can eat the food at its current position and updates the game state accordingly.
     * @return true if Pacman ate the food, false otherwise.
     */
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

            if(element.getSymbol() == Obstacles.POWER.getSymbol()) {
                pacman.setPower(true);
            }

            if(element.getSymbol() == Obstacles.BALL.getSymbol()) {
                eatBallsCounter++;
                SoundManager.play("pacman_chomp.mp3");
            }

            if(element.getSymbol() == Obstacles.FRUIT.getSymbol()) {
                SoundManager.play("pacman_eatFruit.mp3");
            }

            maze.set(pacmanPosition.getPosY(), pacmanPosition.getPosX(), null);
            decFoodRemaining();
        }
        return false;
    }

    /**
     * Increments the score based on the type of food element.
     * @param element The food element.
     */
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

    /**
     * Changes the lock state of all the ghosts in the game state.
     * @param operation The lock state to set for the ghosts.
     * @return true if the lock state was changed, false otherwise.
     */
    public boolean changeLockGhosts(Boolean operation) {

        if(ghosts == null)
            return false;

        for(Ghost a : ghosts)
            a.setLocked(operation);

        return true;
    }

    /**
     * Sets the vulnerable state for all the ghosts in the game state.
     * @param state The vulnerable state to set for the ghosts.
     */
    public void ghostsVulnerable(boolean state){
        for(Ghost ghost: ghosts)
            ghost.setVulnerable(state);
    }

    /**
     * Manages the state of Pacman when it loses a life.
     * @return 1 if Pacman has lives remaining, -1 if it is game over.
     */
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

    /**
     * Resets the ghosts in the game state.
     */
    private void resetGhosts(){
        for(Ghost ghost : ghosts){
            ghost.reset();
        }
    }
    /**
     * Resets Pacman in the game state.
     */
    private void resetPacman() {
        this.pacman.reset();
    }


    /**
     * Resets the level by resetting the ghosts and Pacman.
     */
    private void resetLevel() {
        resetGhosts();
        resetPacman();
    }

    /**
     * Retrieves the number of lives remaining for the player.
     * @return The number of lives remaining.
     */
    public Integer getLives() {
        return lives;
    }

    /**
     * Checks if there are any lives remaining for the player.
     * @return true if there are lives remaining, false otherwise.
     */
    public Boolean isAnyLiveRemaining(){
        return getLives() > 0;
    }

    /**
     * Retrieves the character symbol at the specified position in the game state.
     * @param row The row index of the position.
     * @param column The column index of the position.
     * @return The character symbol at the position.
     */
    public char getCharElementInPosition(int row, int column) {
        return getCharAtMazeElement(row,column);
    }


    /**
     * Retrieves the direction of Pacman in the game state.
     * @return The direction of Pacman.
     */
    public Direction getDirection() {
        return pacman.getDirection();
    }

    /**
     * Checks if the specified character represents a ghost in the game state.
     * @param c The character to check.
     * @return true if the character represents a ghost, false otherwise.
     */
    public boolean charIsGhosts(char c) {
        return c == Obstacles.BLINKY.getSymbol() ||
                c == Obstacles.PINKY.getSymbol() ||
                c == Obstacles.CLYDE.getSymbol() ||
                c == Obstacles.INKY.getSymbol();
    }

    /**
     * Retrieves a random warp position from the available warp positions.
     * @param position The current position.
     * @return A random warp position.
     */
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


    /**
     * Clears all the warp positions in the game state.
     */
    public void clearWarps() {
        warps.clear();
    }

    /**
     * Sets the power state for Pacman in the game state.
     * @param state The power state to set for Pacman.
     */
    public void setPacmanPower(boolean state) {
        pacman.setPower(state);
    }

    /**
     * Checks if the specified position is a vulnerable ghost position in the game state.
     * @param posX The X coordinate of the position.
     * @param posY The Y coordinate of the position.
     * @return true if the position is a vulnerable ghost position, false otherwise.
     */
    public boolean isVulnerableGhostPosition(int posX, int posY) {
        if(maze == null)
            return false;

        for(Ghost aux: ghosts){
            if(aux.getPosX() == posX && aux.getPosY() == posY && aux.getVulnerable())
                return true;
        }
        return false;
    }

    /**
     * Checks if the specified position is a dead ghost position in the game state.
     * @param posX The X coordinate of the position.
     * @param posY The Y coordinate of the position.
     * @return true if the position is a dead ghost position, false otherwise.
     */
    public boolean isGhostDead(int posX, int posY) {
        if(maze == null)
            return false;

        for(Ghost aux: ghosts){
            if(aux.getPosX() == posX && aux.getPosY() == posY && aux.getDead())
                return true;
        }
        return false;
    }

    /**
     * Retrieves the current position of Pacman in the game state.
     * @return The current position of Pacman.
     */
    public Position getPacmanPosition() {
        return pacman.getCurrentPosition();
    }
}