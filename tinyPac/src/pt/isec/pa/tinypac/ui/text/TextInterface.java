package pt.isec.pa.tinypac.ui.text;

import pt.isec.pa.tinypac.model.fsm.Context;
import utils.PAInput;

public class TextInterface {

    Context fsm;

    public TextInterface(Context fsm) {
        this.fsm = fsm;
    }

    public void start() {

        do{
            switch (PAInput.chooseOption("**** Tiny-PAC ****" , "Play Game" , "Top5" , "Exit")){
                case 1 -> {
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
