package pt.isec.pa.tinypac.ui.text;

import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.fsm.Context;
import utils.PAInput;

import java.io.IOException;

public class TextInterface {

    private Context fsm;
    boolean finish = false;


    public TextInterface() {
       // this.fsm = null;
    }

    public void game(){
        while (!finish){
            switch (fsm.getState()){
                case WAIT_TO_MOVE_PACMAN -> waitToMovePacman();
            }
        }
    }

    private void waitToMovePacman() {

        fsm.startGame();
    }

    public void start() throws IOException {

        do{
            switch (PAInput.chooseOption("**** Tiny-PAC ****" , "Play Game" , "Top5" , "Exit")){
                case 1 -> {
                    //GameLanternaUI gameLanternaUI = new GameLanternaUI();

                    this.fsm = new Context();
                    if(!fsm.startGame())
                        System.out.println("Cant start the game\n");
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
}
