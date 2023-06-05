package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.ghosts.Pinky;
import pt.isec.pa.tinypac.model.data.obstacles.*;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Obstacles;

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

        ArrayList<Integer[]> ghostCave = new ArrayList<>();

        Integer pacmanCounter = 0;
        Integer portalCounter = 0;

        for(int i = 0 ; i < game.getMazeRows() ; i++) {       //Y
            sb.deleteCharAt(sb.indexOf("\n",i * game.getMazeColumns()));
            for(int a = 0; a < game.getMazeColumns(); a++) {      //X
                char c = sb.charAt((i * game.getMazeColumns()) + a);
                switch (c) {
                    case 'x' ->   //Parede
                            maze.set(i, a, new Wall());
                    case 'W' ->   //Zona Warp
                            maze.set(i, a, new Warp());
                    case 'o' ->  {  //Comida
                            maze.set(i, a, new Ball());
                            //maze.set(i, a, null);
                            game.incFoodRemaining();
                    }
                    case 'F' ->   //fruta
                            maze.set(i, a, new Fruit());
                    case 'M' -> {   //LocalPacmanInicial
                        maze.set(i, a, new PacmanInitialPosition());
                        game.setPacman(new Pacman(game,a, i));
                        pacmanCounter++;
                    }
                    case 'O' ->   //Bola com Poderes
                            maze.set(i, a, new Power());
                    case 'Y' -> {   //Portal
                        Portal portal = new Portal(a,i); //x,y
                        maze.set(i, a, portal);
                        game.setPortal(portal);
                        portalCounter++;
                    }
                    case 'y' -> {   //Caverna dos Fantasmas
                        maze.set(i, a, new GhostCave());
                        ghostCave.add(new Integer[]{a, i});//x , y
                    }
                    default -> {
                        return null; //Character Invalid
                    }
                }
            }
        }

        if(pacmanCounter != 1 || portalCounter == 0)
            return null;

        game.setGhosts(ghostInitialPositioning(maze,ghostCave));

        //System.out.println(ghostCave.toString());
        return maze;
    }

    public ArrayList<Ghost> ghostInitialPositioning(Maze maze,ArrayList<Integer[]> ghostCave){

        if(maze == null || ghostCave == null || ghostCave.isEmpty())
            return null;

        ArrayList<Ghost> ghosts = new ArrayList<>();

        int numPositions = 0;
        Integer[] randomPosition;
        Ghost ghost = null;

        //Posicionar o Blinky por tras da porta, pois ele so anda para a frente
        for(int i = 0; i < ghostCave.size() ; i++){
            randomPosition = ghostCave.get(i);
            IMazeElement element = maze.get(randomPosition[1] - 1, randomPosition[0]);
            if(element == null)
                continue;

            if(element.getSymbol() == Obstacles.PORTAL.getSymbol() || i == ghostCave.size()) {
                ghost = new Blinky(game,randomPosition[0], randomPosition[1]);
                ghosts.add(ghost);
                ghostCave.remove(randomPosition);
                break;
            }
        }


        for(int i = 0; i < 3;i++){

            IMazeElement element = null;
            do {
                numPositions = ghostCave.size();
                int randomIndex = (int) (Math.random() * numPositions);
                randomPosition = ghostCave.get(randomIndex);
                element = maze.get(randomPosition[0], randomPosition[1]);
            } while (element == null || element.getSymbol() != 'y');

            switch (i){
                case 0: {
                    ghost = new Clyde(game, randomPosition[0], randomPosition[1]);
                    break;
                }
                case 1: {
                    ghost = new Inky(game, randomPosition[0], randomPosition[1]);
                    break;
                }
                case 2: {
                    ghost = new Pinky(game, randomPosition[0], randomPosition[1]);
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

    public void evolve(long currentTime) {
        if(game != null)
            game.evolve();
    }

    public Integer controlGame(){
        return game.controlGame();
    }

    public void ghostsVulnerable(){
        game.ghostsVulnerable();
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

        return true;
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

    public void loadSavedGame() {
    }
}