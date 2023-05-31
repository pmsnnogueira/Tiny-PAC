package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.ui.gui.MainJFX;
import pt.isec.pa.tinypac.ui.guiLanterna.GameLanternaUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Application.launch(MainJFX.class,args);

        /*try {

            GameLanternaUI lanternaui = new GameLanternaUI();


        }catch (IOException e){
            e.printStackTrace();
        }*/
    }
}