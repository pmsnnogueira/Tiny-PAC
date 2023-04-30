package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.ui.text.GameLanternaUI;
import pt.isec.pa.tinypac.ui.text.TextInterface;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            //TextInterface ui = new TextInterface();
            //ui.start();

            //GameManager gameManager = new GameManager();

            GameManager gameManager = new GameManager();
            GameLanternaUI lanternaui = new GameLanternaUI(gameManager);



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