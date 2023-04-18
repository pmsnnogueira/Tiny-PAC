package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.obstacles.*;

import java.io.*;
import java.util.ArrayList;

public class Game {
    private final static String LEVELS_PATH = "src/pt/isec/pa/tinypac/levels/";
    private Integer level;
    private Integer points;
    private Maze maze;
    private Integer mazeRows;
    private Integer mazeColumns;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts;

    public Game(){
        this.level = 1;
        this.points = 0;
        this.maze = null;
        this.ghosts = new ArrayList<>();
        this.pacman = new Pacman();
        newMapLevel();
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

    public boolean newMapLevel(){
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

        ArrayList<Integer> ghostCave = new ArrayList<>();

        for(int i = 0 ; i < mazeRows ; i++) {
            sb.deleteCharAt(sb.indexOf("\n",i * mazeColumns));
            for(int a = 0; a < mazeColumns; a++) {
                char c = sb.charAt((i * mazeColumns) + a);
                switch (c) {
                    case 'x':   //Parede
                        maze.set(i, a, new Wall());
                        break;
                    case 'W':   //Zona Warp
                        maze.set(i, a, new Warp());
                        break;
                    case 'o':   //Comida
                        maze.set(i, a, new Ball());
                        break;
                    case 'F':   //fruta
                        maze.set(i, a, new Fruit());
                        break;
                    case 'M':   //LocalPacmanInicial
                        maze.set(i, a, new PacmanInitialPosition());
                        this.pacman.setPos(i , a);
                        break;
                    case 'O':   //Bola com Poderes
                        maze.set(i, a, new Power());
                        break;
                    case 'Y': {   //Portal
                        maze.set(i, a, new Portal());
                        int pos = (i * mazeColumns) + a;
                        Blinky blinky = new Blinky(1,2);
                        if(pos < sb.length() ){
                            ghosts.add(blinky);
                            //maze.set((pos)/mazeColumns , (pos)%mazeColumns , blinky);
                        }
                        break;
                    }
                    case 'y':   //Caverna dos Fantasmas

                        break;
                    default:
                        return false; //Character Invalid
                }
            }
        }

        //System.out.println(ghostCave.toString());
        return true;
    }


    public void printMaze(){
        char[][]gameBoard;
        gameBoard = maze.getMaze();

        for(int i = 0 ; i < mazeRows; i++) {
            for (int a = 0; a < mazeColumns; a++) {
                System.out.print(gameBoard[i][a]);
            }
            System.out.println();
        }
    }
    

    private boolean insertGhosts(int posX, int posY){

        //BLINKY



        return true;
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
