package pt.isec.pa.tinypac.ui.text;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.fsm.Context;
import utils.PAInput;

import java.io.IOException;

public class TextInterface implements IGameEngineEvolve{

    private Context fsm;
    boolean finish = false;


    public TextInterface() {
       // this.fsm = null;
    }

    public void gameMenu() throws InterruptedException {
        while (!finish){
            switch (fsm.getState()){
                case WAIT_TO_MOVE_PACMAN -> waitToMovePacmaUI();
                case GAME -> gameUI();
            }
        }
    }

    private void waitToMovePacmaUI() {

    }


    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public void gameUI() throws InterruptedException {


        printBoard();
        finish = true;

    }

    private void printBoard(){
        char[][] maze = fsm.showMaze();
        if(maze == null)
            return;

        for(int i = 0; i < maze.length;i++){
            for(int a = 0; a < maze[0].length; a++){
                System.out.print(maze[i][a]);
            }
            System.out.println();
        }
    }

    public void start() throws IOException, InterruptedException {

        do{
            switch (PAInput.chooseOption("**** Tiny-PAC ****" , "Play Game" , "Top5" , "Exit")){
                case 1 -> {
                    GameLanternaUI gameLanternaUI = new GameLanternaUI();
                   // this.fsm = new Context();
                    //gameMenu();


                    /*switch (fsm.getState()){
                        case
                    }*/
                }
                case 2 -> {

                }
                case 3 -> {
                    return;
                }
            }
        }while (true);
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {

    }
}
