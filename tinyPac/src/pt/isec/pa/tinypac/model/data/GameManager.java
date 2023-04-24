package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.ghosts.Pinky;
import pt.isec.pa.tinypac.model.data.obstacles.*;
import utils.Obstacles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager implements IGameEngineEvolve {
    private Game game;
    private GameEngine gameEngine;
    private final static String LEVELS_PATH = "src/pt/isec/pa/tinypac/levels/";

    public GameManager(GameEngine gameEngine){
        this.game = new Game();
        //this.gameEngine = gameEngine;
        //gameEngine.registerClient(this);
        //gameEngine.start(2000);
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
        int counter = game.getLevel();

        if(listOfFiles == null)
            return false;

        do {
            fileName.delete(0, fileName.length());
            fileName.append("Level").append((counter < 10) ? "0" + counter : + counter).append(".txt");
            counter--;
        } while (!listOfFiles.contains(fileName.toString()));


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
            bufferedReader.close(); //não é necessário
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        game.setMazeRows(rows);
        game.setMazeColumns(columns);

        System.out.println("Game Rows: " + rows + " Columns: " + columns);
        return setCharInMap(stringBuilder);
    }

    private Maze setCharInMap(StringBuilder sb){

        Maze maze = new Maze(game.getMazeRows() , game.getMazeColumns());

        ArrayList<Integer[]> ghostCave = new ArrayList<>();

        for(int i = 0 ; i < game.getMazeRows() ; i++) {       //Y
            sb.deleteCharAt(sb.indexOf("\n",i * game.getMazeColumns()));
            for(int a = 0; a < game.getMazeColumns(); a++) {      //X
                char c = sb.charAt((i * game.getMazeColumns()) + a);
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
                        game.setPacman(new Pacman(game,a, i));
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
                        return null; //Character Invalid
                    }
                }
            }
        }

        game.setGhosts(ghostInitialPositioning(maze,ghostCave));

        //System.out.println(ghostCave.toString());
        return maze;
    }

    public ArrayList<Ghost> ghostInitialPositioning(Maze maze,ArrayList<Integer[]> ghostCave){

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
                ghost = new Blinky(game,randomPosition[0], randomPosition[1] - 1);
                ghosts.add(ghost);
                ghostCave.remove(randomPosition);
                break;
            }
        }


        for(int i = 0; i < 3;i++){
            do {
                numPositions = ghostCave.size();
                int randomIndex = (int) (Math.random() * numPositions);
                randomPosition = ghostCave.get(randomIndex);
            } while (maze.get(randomPosition[0], randomPosition[1]).getSymbol() != 'y');

            switch (i){
                case 0:
                    ghost = new Clyde(game,randomPosition[0] , randomPosition[1]);
                    break;
                case 1:
                    ghost = new Inky(game,randomPosition[0] , randomPosition[1]);
                    break;
                case 2:
                    ghost = new Pinky(game,randomPosition[0] , randomPosition[1]);
                    break;
            }
            ghosts.add(ghost);
            //maze.set(randomPosition[0] , randomPosition[1] , ghost);
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


    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        if(game == null)
            return;

        if(!game.evolve())
            gameEngine.stop();
    }

}
