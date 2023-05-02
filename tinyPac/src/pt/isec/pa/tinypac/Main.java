package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.ui.gui.GameLanternaUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            //TextInterface ui = new TextInterface();
            //ui.start();

            //GameManager gameManager = new GameManager();

            GameLanternaUI lanternaui = new GameLanternaUI();



           // IGameEngine gameEngine = new GameEngine();

            /*gameEngine.registerClient(gameManager);
            //gameEngine.registerClient(Lanternaui);
            gameEngine.start(500);
            gameEngine.waitForTheEnd();*/

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}