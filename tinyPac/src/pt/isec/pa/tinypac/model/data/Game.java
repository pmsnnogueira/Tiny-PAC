package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.ghosts.Pinky;
import pt.isec.pa.tinypac.model.data.obstacles.*;
import utils.Obstacles;

import java.io.*;
import java.util.ArrayList;

public class Game {
    private final static String LEVELS_PATH = "src/pt/isec/pa/tinypac/levels/";
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

    public boolean generateMapLevel(){
        //verificar se existem ficheiros dos mapas
        ArrayList<String> listOfFiles = filesinFolder(LEVELS_PATH);
        StringBuilder fileName = new StringBuilder();
        int counter = level;

        if(listOfFiles == null)
            return false;

        do {
            fileName.delete(0, fileName.length());
            fileName.append("Level").append((counter < 10) ? "0" + counter : + counter).append(".txt");
            counter--;
        } while (!listOfFiles.contains(fileName.toString()));


        fileName.insert(0 , LEVELS_PATH);

        //Verificar para todos os ficheiros com o directory.listFiles
        if(!buildMap(fileName.toString()))
            return false;

        // insertGhosts();

        printMaze();


        return true;
    }


    private Boolean buildMap(String fileName){

        int rows = 0, columns = 0;
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int counter = 0;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");

                if (counter != 0 && columns != line.length()) {
                    return false;
                }
                rows = ++counter;
                columns = line.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        this.mazeRows = rows ;
        this.mazeColumns = columns;
        maze = new Maze(rows ,columns);

        if(!setCharInMap(stringBuilder))
            return false;

        System.out.println("Game Rows: " + rows + " Columns: " + columns);
        return true;
    }

    private boolean setCharInMap(StringBuilder sb){

        ArrayList<Integer[]> ghostCave = new ArrayList<>();

        for(int i = 0 ; i < mazeRows ; i++) {       //Y
            sb.deleteCharAt(sb.indexOf("\n",i * mazeColumns));
            for(int a = 0; a < mazeColumns; a++) {      //X
                char c = sb.charAt((i * mazeColumns) + a);
                switch (c) {
                    case 'x' ->   //Parede
                            maze.set(i, a, new Wall());
                    case 'W' ->   //Zona Warp
                            maze.set(i, a, new Warp());
                    case 'o' ->   //Comida
                            maze.set(i, a, new Ball());
                    case 'F' ->   //fruta
                            maze.set(i, a, new Fruit());
                    case 'M' -> {   //LocalPacmanInicial
                        maze.set(i, a, new PacmanInitialPosition());
                        this.pacman = new Pacman(a, i);
                    }
                    case 'O' ->   //Bola com Poderes
                            maze.set(i, a, new Power());
                    case 'Y' -> {   //Portal
                        maze.set(i, a, new Portal());
                    }
                    case 'y' -> {   //Caverna dos Fantasmas
                        maze.set(i, a, new GhostCave());
                        ghostCave.add(new Integer[]{a, i});//x , y
                    }
                    default -> {
                        return false; //Character Invalid
                    }
                }
            }
        }


        int numPositions = 0;
        Integer[] randomPosition;
        Ghost ghost = null;

        //Posicionar o Blinky por tras da porta, pois ele so anda para a frente
        for(int i = 0; i < ghostCave.size() ; i++){
            randomPosition = ghostCave.get(i);
            IMazeElement element = maze.get(randomPosition[1] - 1, randomPosition[0]);
            if(element == null)
                continue;

            if(element.getSymbol() == Obstacles.PORTAL.getSymbol()) {
                ghost = new Blinky(randomPosition[0], randomPosition[1]);
                ghosts.add(ghost);
                ghostCave.remove(randomPosition);
                break;
            }

            if(i == ghostCave.size())
                return false;
        }


        for(int i = 0; i < 3;i++){
            do {
                numPositions = ghostCave.size();
                int randomIndex = (int) (Math.random() * numPositions);
                randomPosition = ghostCave.get(randomIndex);
            } while (maze.get(randomPosition[0], randomPosition[1]).getSymbol() != 'y');

            switch (i){
                case 0:
                    ghost = new Clyde(randomPosition[0] , randomPosition[1]);
                    break;
                case 1:
                    ghost = new Inky(randomPosition[0] , randomPosition[1]);
                    break;
                case 2:
                    ghost = new Pinky(randomPosition[0] , randomPosition[1]);
                    break;
            }
            ghosts.add(ghost);
            //maze.set(randomPosition[0] , randomPosition[1] , ghost);
            ghostCave.remove(randomPosition);
        }


        //System.out.println(ghostCave.toString());
        return true;
    }


    public void printMaze() {
        char[][] gameBoard;
        gameBoard = maze.getMaze();

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
                    System.out.print(' ');
                    continue;
                }

                System.out.print(gameBoard[i][a]);
            }
            System.out.println();
        }
    }

    private boolean insertGhosts(int posX, int posY){

        //BLINKY



        return true;
    }

    public void makeGhostMovements(){
        for(Ghost a : ghosts){
            a.move(maze,mazeRows,mazeColumns);
        }
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
}
