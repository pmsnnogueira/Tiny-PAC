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
            System.out.println("**** Tiny-PAC ****");
            switch (PAInput.chooseOption("Menu" , "Play Game" , "Top5" , "Exit")){
                case 1 -> {

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
