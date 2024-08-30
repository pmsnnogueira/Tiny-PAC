package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.ghosts.Pinky;
import pt.isec.pa.tinypac.model.data.obstacles.*;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.GameStatus;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.Position;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * The GameManager class manages the game and provides game functionalities.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class GameManager{
    private Game game;
    private final static String LEVELS_PATH = "files/levels/";

    private final static String SAVE_PATH = "files/saves/";
    private final static String SAVE_NAME = "tiny_Pac01.json";

    /**
     * Constructs a GameManager object with a new game instance.
     */
    public GameManager(){
        this.game = new Game();
    }

    /**
     * Constructs a GameManager object with an existing game instance.
     * @param game The game instance.
     */
    public GameManager(Game game){
        this.game = game;
    }


    /**
     * Unlocks or locks the ghosts based on the specified operation.
     * @param operation The operation to perform (true to unlock, false to lock).
     * @return True if the operation is successful, false otherwise.
     */
    public boolean unlockGosts(Boolean operation){
        return game.changeLockGhosts(operation);
    }


    /**«
     * Changes the direction of the Pacman.
     * @param direction The direction to change to.
     * @return True if the direction is successfully changed, false otherwise.
     */
    public boolean changeDirection(Direction direction){

        if(game.changeDirection(direction))
            return true;
        return false;
    }

    /**
     * Returns a copy of the current game instance.
     * @return A copy of the game instance.
     */
    public Game getGame() {
        return new Game(game);
    }

    /**
     * Returns a list of files in the specified folder.
     * @param folderName The name of the folder
     * @return An ArrayList containing the names of the files in the folder.
     */
    private ArrayList<String> filesinFolder(String folderName){

        File fileFolder = new File(folderName);
        ArrayList<String> listOfFiles = new ArrayList<>();

        if(fileFolder.exists() && fileFolder.isDirectory()){
            File[] a = fileFolder.listFiles();
            if(a != null && a.length > 0){
                for(File obj : a){
                    listOfFiles.add(obj.getName());
                }
                return listOfFiles;
            }
        }
        return null;
    }

    /**
     * Loads a map level from the default levels path.
     * @return True if the map level is successfully loaded, false otherwise.
     */
    public boolean loadMapLevel(){
        return loadMapLevel(LEVELS_PATH);
    }

    /**
     * Loads a map level from the specified folder path.
     * @param folder The path of the folder containing the map levels.
     * @return True if the map level is successfully loaded, false otherwise.
     */
    public boolean loadMapLevel(String folder){

        if(folder == null)
            return false;

        //verificar se existem ficheiros dos mapas
        if(game.isAnyFoodRemaining() && game.isAnyLiveRemaining())   //Load the Same Level
            return true;

        ArrayList<String> listOfFiles = filesinFolder(folder);
        StringBuilder fileName = new StringBuilder();
        int counter = game.getLevel();

        if(listOfFiles == null)
            return false;

       for(int i = 0; i < listOfFiles.size(); i++){
            fileName.delete(0, fileName.length());      //Remover a barra
            fileName.append("Level").append((counter < 10) ? "0" + counter : + counter).append(".txt");
            counter--;

            if(listOfFiles.contains(fileName.toString()))
                break;


            if(i == listOfFiles.size() - 1)     //Nao encontrou nenhum ficheiro com o nome pretendido
                return false;
        }

        fileName.insert(0 , LEVELS_PATH);

        //Verificar para todos os ficheiros com o directory.listFiles
        game.setMaze(buildMap(fileName.toString()));
        if(game.getMaze() == null)
            return false;

        // insertGhosts();

        // printMaze();
        //printMaze();


        return true;
    }

    /**
     * Builds a maze map from the specified file.
     * @param fileName The name of the file containing the map.
     * @return The constructed Maze object if successful, null otherwise.
     */
    private Maze buildMap(String fileName){

        int rows = 0, columns = 0;
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            Scanner sc = new Scanner(bufferedReader);
            int counter = 0;

            while (sc.hasNext()) {
                line = sc.nextLine();
                stringBuilder.append(line);
                stringBuilder.append("\n");

                if (counter != 0 && columns != line.length()) {
                    return null;
                }
                rows = ++counter;
                columns = line.length();
            }
            sc.close();
            bufferedReader.close(); //não é necessário por causa do tryCatch
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        game.setMazeRows(rows);
        game.setMazeColumns(columns);

        //System.out.println("Game Rows: " + rows + " Columns: " + columns);
        return setCharInMap(stringBuilder);
    }

    /**
     * Sets the characters in the maze map based on the provided StringBuilder.
     * @param sb The StringBuilder containing the characters.
     * @return The constructed Maze object if successful, null otherwise.
     */
    private Maze setCharInMap(StringBuilder sb){

        Maze maze = new Maze(game.getMazeRows() , game.getMazeColumns());

        ArrayList<Position> ghostCave = new ArrayList<>();
        game.clearWarps();


        Integer pacmanCounter = 0;
        Integer portalCounter = 0;

        for(int posY = 0 ; posY < game.getMazeRows() ; posY++) {       //Y
            sb.deleteCharAt(sb.indexOf("\n",posY * game.getMazeColumns()));
            for(int posX = 0; posX < game.getMazeColumns(); posX++) {      //X
                char c = sb.charAt((posY * game.getMazeColumns()) + posX);
                switch (c) {
                    case 'x' ->   //Parede
                        maze.set(posY, posX, new Wall());
                    case 'W' ->  { //Zona Warp
                        Warp warp = new Warp(posX,posY);
                        maze.set(posY, posX, warp);
                        game.addWarps(warp);
                    }
                    case 'o' ->  {  //Comida
                        maze.set(posY, posX, new Ball());
                        //maze.set(i, a, null);
                        game.incFoodRemaining();
                    }
                    case 'F' -> {  //fruta
                        maze.set(posY, posX, new Fruit());
                        game.incFoodRemaining();
                    }
                    case 'M' -> {   //LocalPacmanInicial
                        maze.set(posY, posX, new PacmanInitialPosition());
                        game.setPacman(new Pacman(game,posX, posY));
                        pacmanCounter++;
                    }
                    case 'O' -> {   //Bola com Poderes
                        maze.set(posY, posX, new Power());
                        game.incFoodRemaining();
                    }
                    case 'Y' -> {   //Portal
                        Portal portal = new Portal(posX,posY); //x,y
                        maze.set(posY, posX, portal);
                        game.setPortal(portal);
                        portalCounter++;
                    }
                    case 'y' -> {   //Caverna dos Fantasmas
                        maze.set(posY, posX, new GhostCave());
                        ghostCave.add(new Position(posX,posY));//x , y

                    }
                    default -> {
                        return null; //Character Invalid
                    }
                }
            }
        }

        if(pacmanCounter != 1 || portalCounter == 0 || (game.getSizeWarps() % 2 != 0))
            return null;

        game.setGhosts(ghostInitialPositioning(maze,ghostCave));
        return maze;
    }

    /**
     * Determines the initial positions for the ghosts in the maze.
     * @param maze The maze object.
     * @param ghostCave The list of available positions in the ghost cave.
     * @return The list of Ghost objects with their initial positions if successful, null otherwise.
     */
    public ArrayList<Ghost> ghostInitialPositioning(Maze maze,ArrayList<Position> ghostCave){

        if(maze == null || ghostCave == null || ghostCave.isEmpty())
            return null;

        ArrayList<Ghost> ghosts = new ArrayList<>();

        int numPositions = 0;
        Position randomPosition;
        Ghost ghost = null;

        for(int i = 0; i < 4;i++){

            IMazeElement element = null;
            do {
                numPositions = ghostCave.size();
                int randomIndex = (int) (Math.random() * numPositions);
                randomPosition = ghostCave.get(randomIndex);
                element = maze.get(randomPosition.getPosY(), randomPosition.getPosX());           //y, x
            } while (element == null || element.getSymbol() != Obstacles.GHOST_CAVE.getSymbol());

            switch (i){
                case 0: {
                    ghost = new Clyde(game,  randomPosition.getPosX(), randomPosition.getPosY());
                    break;
                }
                case 1: {
                    ghost = new Inky(game,  randomPosition.getPosX(), randomPosition.getPosY());
                    break;
                }
                case 2: {
                    ghost = new Pinky(game,  randomPosition.getPosX(), randomPosition.getPosY());
                    break;
                }
                case 3: {
                    ghost = new Blinky(game, randomPosition.getPosX(), randomPosition.getPosY());
                    break;
                }
            }
            ghosts.add(ghost);
            ghostCave.remove(randomPosition);
        }

        return ghosts;
    }

    /**
     * Retrieves the current maze map of the game.
     * @return The maze map as a bi dimensional character array.
     */
    public char[][] showMaze(){
       return game.showMaze();
    }

    /**
     * Retrieves the information about the current game information.
     * @return A string containing the game information.
     */
    public String showGameInfo(){
        return game.showGameInfo();
    }

    /**
     * Evolves the game state.
     * @param currentTime The current time in milliseconds.
     * @return True if the game state is successfully evolved, false otherwise.
     */
    public GameStatus evolve(long currentTime) {
        if(game != null)
            return game.evolve();
        return GameStatus.ERROR;
    }

    /**
     * Sets the vulnerability state of the ghosts in the game.
     * @param state True to make the ghosts vulnerable, false otherwise.
     */
    public void ghostsVulnerable(boolean state){
        game.ghostsVulnerable(state);
    }

    /**
     * Manages the movement of the Pacman character in the game.
     * @return The result code indicating the movement outcome.
     */
    public int pacmanManager() {
        return game.pacmanManager();
    }

    /**
     * Retrieves the number of rows in the maze map.
     * @return The number of rows in the maze.
     */
    public Integer getMazeRows() {
        return game.getMazeRows();
    }

    /**
     * Retrieves the number of columns in the maze map.
     * @return The number of columns in the maze.
     */
    public Integer getMazeColumns() {
        return game.getMazeColumns();
    }

    /**
     * Retrieves the character element at the specified position in the maze map.
     * @param row The row index.
     * @param column The column index.
     * @return The character element at the specified position.
     */
    public char receiveElement(int row, int column) {
        return game.getCharElementInPosition(row,column);
    }

    /**
     * Retrieves the current direction of pacman in the game.
     * @return The current direction.
     */
    public Direction getDirection() {
        return game.getDirection();
    }

    /**
     * Checks if the given character represents a ghost in the game.
     * @param c The character to check.
     * @return True if the character represents a ghost, false otherwise.
     */
    public boolean charIsGhosts(char c) {
        return game.charIsGhosts(c);
    }

    /**
     * Retrieves the current score in the game.
     * @return The current score.
     */
    public int getScore() {
        return game.getScore();
    }

    /**
     * Checks if there are saved game files in the system.
     * @return True if saved game files exist, false otherwise.
     */
    public boolean checkIfSavedGamesExist() {

        File f = new File(SAVE_PATH + SAVE_NAME);
        if(f.exists())
            return true;

        return false;
    }

    /**
     * Retrieves the current vulnerable state of the game.
     * @return The vulnerable state of the game.
     */
    public int controlGameVulnerableState() {
        return game.controlGameVulnerableState();
    }

    /**
     * Retrieves the current state of the game.
     * @return The state of the game.
     */
    public int controlGameState() {
        return game.controlGameState();
    }

    /**
     * Sets the power state of the Pacman character.
     * @param b The power state to set.
     */
    public void setPacmanPower(boolean b) {
        game.setPacmanPower(b);
    }

    /**
     * Checks if the specified position in the maze map represents a vulnerable ghost.
     * @param posX The X position.
     * @param posY The Y position.
     * @return True if the position represents a vulnerable ghost, false otherwise.
     */
    public boolean isVulnerableGhostPosition(int posX, int posY) {
        return game.isVulnerableGhostPosition(posX, posY);
    }

    /**
     * Checks if the specified position in the maze map represents a dead ghost.
     * @param posX The X position.
     * @param posY The Y position.
     * @return True if the position represents a dead ghost, false otherwise.
     */
    public boolean isGhostDead(int posX, int posY) {
        return game.isGhostDead(posX, posY);
    }

    /**
     * Removes the saved game file.
     */
    public void removeSavedGame() {

        File file = new File(SAVE_PATH + SAVE_NAME);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * Checks if the current score is within the top 5 scores.
     * @return True if the score is in the top 5, false otherwise.
     */
    public boolean isInTop5() {
        return new Top5().verifyIfIsInTop5(game.getScore());
    }

    /**
     * Adds the current player into the top 5 scores list.
     * @param userName The name of the user.
     */
    public void addIntoTop5(String userName) {
        new Top5().addIntoTop5(userName,getScore());
    }

}