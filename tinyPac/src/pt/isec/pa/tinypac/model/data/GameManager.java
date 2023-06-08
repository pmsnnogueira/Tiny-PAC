package pt.isec.pa.tinypac.model.data;

import javafx.geometry.Pos;
import pt.isec.pa.tinypac.model.data.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.ghosts.Pinky;
import pt.isec.pa.tinypac.model.data.obstacles.*;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;
import pt.isec.pa.tinypac.utils.Position;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager{
    private Game game;
    private final static String LEVELS_PATH = "files/levels/";

    private final static String SAVE_PATH = "files/saves/";
    private final static String SAVE_NAME = "tiny_Pac01.json";

    public GameManager(){
        this.game = new Game();
    }


    public boolean unlockGosts(Boolean operation){
        return game.changeLockGhosts(operation);
    }


    public boolean changeDirection(Direction direction){

        if(game.changeDirection(direction))
            return true;
        return false;
    }

    public Game getGame() {
        return new Game(game);
    }

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

    public boolean loadMapLevel(){
        return loadMapLevel(LEVELS_PATH);
    }

    public boolean loadMapLevel(String folder){

        if(folder == null)
            return false;

        //verificar se existem ficheiros dos mapas
        if(game.isAnyFoodRemaining() && game.isAnyLiveRemaining())   //Load the Same Level
            return true;

        ArrayList<String> listOfFiles = filesinFolder(folder);
        StringBuilder fileName = new StringBuilder();
        int counter = game.getLevel();
        System.out.println("Level:" + game.getLevel());

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

    public Maze getMaze(){
        return game.getMaze();
    }


    public char[][] showMaze(){
       return game.showMaze();
    }


    public String showGameInfo(){
        return game.showGameInfo();
    }

    public boolean evolve(long currentTime) {
        if(game != null)
            return game.evolve();

        return false;
    }

    public Integer controlGame(){
        return game.controlGame();
    }

    public void ghostsVulnerable(boolean state){
        game.ghostsVulnerable(state);
    }

    public int pacmanManager() {
        return game.pacmanManager();
    }

    public Integer getMazeRows() {
        return game.getMazeRows();
    }
    public Integer getMazeColumns() {
        return game.getMazeColumns();
    }

    public char receiveElement(int row, int column) {
        return game.getCharElementInPosition(row,column);
    }

    public Direction getDirection() {
        return game.getDirection();
    }

    public boolean charIsGhosts(char c) {
        return game.charIsGhosts(c);
    }

    public int getScore() {
        return game.getScore();
    }

    public boolean checkIfSavedGamesExist() {

        File f = new File(SAVE_PATH + SAVE_NAME);
        if(f.exists())
            return true;

        return false;
    }

    public void saveGame(){

        File file = new File(SAVE_PATH + SAVE_NAME);
        if(file != null)
            saveGame(file);

    }

    private void saveGame(File file){

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(file))
        ){
            oos.writeObject(game);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadSavedGame(){

        File file = new File(SAVE_PATH + SAVE_NAME);
        if(file != null)
            loadSavedGame(file);

    }

    private void loadSavedGame(File file) {

        try (ObjectInputStream oos = new ObjectInputStream(
                new FileInputStream(file))
        ){
            game = (Game) oos.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public int controlGameVulnerableState() {
        return game.controlGameVulnerableState();
    }

    public int controlGameState() {
        return game.controlGameState();
    }

    public boolean getPower() {
        return game.getPower();
    }

    public void pacmanPower(boolean b) {
        game.pacmanPower(b);
    }

    public boolean isVulnerableGhostPosition(int posX, int posY) {
        return game.isVulnerableGhostPosition(posX, posY);
    }
}